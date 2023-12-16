package com.projetenchere.common.network;

import com.projetenchere.common.Utils.SerializationUtil;
import org.jetbrains.annotations.Nullable;

import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.io.Serializable;
import java.security.Signature;

public class Client {

    private void checkConnection(SSLSocket socket) {
        if (socket == null) throw new RuntimeException("Trying to send data to closed socket");
    }

    protected <T extends Serializable> T fetch(
            SSLSocket socket,
            Headers headerToSend,
            Headers headerToReceive,
            @Nullable
            Signature signature) {
        checkConnection(socket);
        NetworkData request = new NetworkData(headerToSend, signature);
        DataWrapper<T> wrapped;
        try {
            SerializationUtil.serializeTo(request, socket);
            wrapped = SerializationUtil.deserializeAsDataWrapper(socket);
            if (!wrapped.checkHeader(headerToReceive)) throw new RuntimeException("Wrong header received");
        } catch (IOException | ClassCastException | ClassNotFoundException e) {
            throw new RuntimeException("Socket error");
        }
        return wrapped.unwrap();
    }

    protected <T1 extends Serializable, T2 extends Serializable> T1 fetchWithData(
            SSLSocket socket,
            Headers headerToSend,
            Headers headerToReceive,
            T2 data,
            @Nullable
            Signature signature
    ) {
        checkConnection(socket);
        DataWrapper<T1> wrappedToReceive;
        DataWrapper<T2> wrappedToSend = new DataWrapper<>(data, headerToSend, signature);
        try {
            SerializationUtil.serializeTo(wrappedToSend, socket);
            wrappedToReceive = SerializationUtil.deserializeAsDataWrapper(socket);
            if (!wrappedToSend.checkHeader(headerToReceive)) throw new RuntimeException("Wrong header received");
        } catch (IOException | ClassCastException | ClassNotFoundException e) {
            throw new RuntimeException("Socket error");
        }
        return wrappedToReceive.unwrap();
    }
}
