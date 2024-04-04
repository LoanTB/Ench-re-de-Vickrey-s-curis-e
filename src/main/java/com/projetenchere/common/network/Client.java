package com.projetenchere.common.network;

import java.io.IOException;
import java.io.Serializable;

public class Client {

    private void checkConnection(ClientSocketWrapper socket) {
        if (socket == null) throw new RuntimeException("Trying to send data to closed socket");
    }

    public <T extends Serializable> T fetch(ClientSocketWrapper socket, Headers headerToSend, Headers headerToReceive) {
        checkConnection(socket);
        DataWrapper<?> request = new DataWrapper<>(headerToSend);
        DataWrapper<T> wrapped;
        try {
            System.out.println("Sending " + headerToSend);
            socket.getObjectOutputStream().writeObject(request);
            wrapped = (DataWrapper<T>) socket.getObjectInputStream().readObject();
            System.out.println("Received " + wrapped.getHeader());
            if (!wrapped.checkHeader(headerToReceive))
                throw new RuntimeException("Wrong header received: " + headerToReceive);
        } catch (IOException | ClassCastException | ClassNotFoundException e) {
            throw new RuntimeException("Socket error");
        }
        return wrapped.unwrap();
    }

    protected <T1 extends Serializable, T2 extends Serializable> T1 fetchWithData(ClientSocketWrapper socket, Headers headerToSend, Headers headerToReceive, T2 data) {
        checkConnection(socket);
        DataWrapper<T1> wrappedToReceive;
        DataWrapper<T2> wrappedToSend = new DataWrapper<>(data, headerToSend);
        try {
            System.out.println("Sending " + wrappedToSend.getHeader());
            socket.getObjectOutputStream().writeObject(wrappedToSend);
            wrappedToReceive = (DataWrapper<T1>) socket.getObjectInputStream().readObject();
            System.out.println("Received " + wrappedToReceive.getHeader());
            if (!wrappedToReceive.checkHeader(headerToReceive))
                throw new RuntimeException("Wrong header | wanted " + headerToReceive + " received " + wrappedToReceive.getHeader());
        } catch (IOException | ClassCastException | ClassNotFoundException e) {
            throw new RuntimeException("Socket error");
        }
        return wrappedToReceive.unwrap();
    }

    private void stopWithHeader(ClientSocketWrapper clientSocketWrapper, Headers header) {
        checkConnection(clientSocketWrapper);
        DataWrapper<?> dataWrapper = new DataWrapper<>(header);
        try {
            clientSocketWrapper.getObjectOutputStream().writeObject(dataWrapper);
            clientSocketWrapper.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void stop(ClientSocketWrapper clientSocketWrapper) {
        this.stopWithHeader(clientSocketWrapper, Headers.GOODBYE_HAVE_A_NICE_DAY);
    }

    protected void abort(ClientSocketWrapper clientSocketWrapper) {
        this.stopWithHeader(clientSocketWrapper, Headers.ABORT);
    }

}
