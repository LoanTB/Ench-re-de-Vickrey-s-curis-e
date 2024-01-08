package com.projetenchere.common.network;

import com.projetenchere.common.Utils.SerializationUtil;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.security.PublicKey;

public class Client {

    private void checkConnection(ClientSocketWrapper socket) {
        if (socket == null) throw new RuntimeException("Trying to send data to closed socket");
    }

    protected <T extends Serializable> T fetch(
            ClientSocketWrapper socket,
            Headers headerToSend,
            Headers headerToReceive
            ) {
        checkConnection(socket);
        DataWrapper<?> request = new DataWrapper<>(headerToSend);
        DataWrapper<T> wrapped;
        try {
            socket.getObjectOutputStream().writeObject(request);
            wrapped = (DataWrapper<T>) socket.getObjectInputStream().readObject();
            if (!wrapped.checkHeader(headerToReceive)) throw new RuntimeException("Wrong header received: " + headerToReceive);
        } catch (IOException | ClassCastException | ClassNotFoundException e) {
            throw new RuntimeException("Socket error");
        }
        return wrapped.unwrap();
    }

    protected <T1 extends Serializable, T2 extends Serializable> T1 fetchWithData(
            ClientSocketWrapper socket,
            Headers headerToSend,
            Headers headerToReceive,
            T2 data
    ) {
        checkConnection(socket);
        DataWrapper<T1> wrappedToReceive;
        DataWrapper<T2> wrappedToSend = new DataWrapper<>(data, headerToSend);
        try {
            socket.getObjectOutputStream().writeObject(wrappedToSend);
            wrappedToReceive = (DataWrapper<T1>) socket.getObjectInputStream().readObject();
            if (!wrappedToReceive.checkHeader(headerToReceive)) throw new RuntimeException("Wrong header | wanted " + headerToReceive + " received " + wrappedToReceive.getHeader());
        } catch (IOException | ClassCastException | ClassNotFoundException e) {
            throw new RuntimeException("Socket error");
        }
        return wrappedToReceive.unwrap();
    }

    protected void stop(ClientSocketWrapper clientSocketWrapper) {
        checkConnection(clientSocketWrapper);
        DataWrapper<?> dataWrapper = new DataWrapper<>(Headers.GOODBYE_HAVE_A_NICE_DAY);
        try {
            clientSocketWrapper.getObjectOutputStream().writeObject(dataWrapper);
            clientSocketWrapper.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
