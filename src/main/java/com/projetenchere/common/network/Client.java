package com.projetenchere.common.network;

import com.projetenchere.common.Utils.SerializationUtil;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.security.Signature;

public class Client {

    private void checkConnection(Socket socket) {
        if (socket == null) throw new RuntimeException("Trying to send data to closed socket");
    }

    protected <T extends Serializable> T fetch(
            Socket socket,
            Headers headerToSend,
            Headers headerToReceive,
            @Nullable
            Signature signature) {
        checkConnection(socket);
        DataWrapper<?> request = new DataWrapper<>(headerToSend);
        DataWrapper<T> wrapped;
        try {
            SerializationUtil.serializeTo(request, socket);
            wrapped = SerializationUtil.deserialize(socket);
            if (!wrapped.checkHeader(headerToReceive)) throw new RuntimeException("Wrong header received");
        } catch (IOException | ClassCastException | ClassNotFoundException e) {
            throw new RuntimeException("Socket error");
        }
        return wrapped.unwrap();
    }

    protected <T1 extends Serializable, T2 extends Serializable> T1 fetchWithData(
            Socket socket,
            Headers headerToSend,
            Headers headerToReceive,
            T2 data
    ) {
        checkConnection(socket);
        DataWrapper<T1> wrappedToReceive;
        DataWrapper<T2> wrappedToSend = new DataWrapper<>(data, headerToSend);
        try {
            SerializationUtil.serializeTo(wrappedToSend, socket);
            wrappedToReceive = SerializationUtil.deserialize(socket);
            if (!wrappedToSend.checkHeader(headerToReceive)) throw new RuntimeException("Wrong header received");
        } catch (IOException | ClassCastException | ClassNotFoundException e) {
            throw new RuntimeException("Socket error");
        }
        return wrappedToReceive.unwrap();
    }
}
