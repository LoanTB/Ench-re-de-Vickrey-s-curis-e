package com.projetenchere.common.network.Handlers.client;

import com.projetenchere.common.network.DataWrapper;
import com.projetenchere.common.network.Headers;
import com.projetenchere.common.network.Handlers.data.IDataHandler;
import com.projetenchere.common.network.NetworkData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

public class ClientHandler extends Thread{
    private final Map<Headers, IDataHandler> handlers;
    private final ObjectInputStream objectInput;
    private final ObjectOutputStream objectOutput;

    public synchronized void addHandler(Headers header, IDataHandler replyer) {
        this.handlers.put(header, replyer);
    }

    public synchronized void removeHandler(Headers header) {
        this.handlers.remove(header);
    }

    public ClientHandler(
            Map<Headers, IDataHandler> handlers,
            ObjectInputStream objectInput,
            ObjectOutputStream objectOutput
    ) {
        this.handlers = handlers;
        this.objectInput = objectInput;
        this.objectOutput = objectOutput;
    }
    @Override
    public void run() {
        try {
            Object rawRequest = objectInput.readObject();
            NetworkData request = (NetworkData) rawRequest;
            DataWrapper<?> data;
            if (handlers.containsKey(request.getHeader())) {
                data = handlers.get(request.getHeader()).handle();
                if (handlers.get(request.getHeader()).generatesReply()) {
                    objectOutput.writeObject(data);
                }
            }
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            throw new RuntimeException(e);
        }
    }
}
