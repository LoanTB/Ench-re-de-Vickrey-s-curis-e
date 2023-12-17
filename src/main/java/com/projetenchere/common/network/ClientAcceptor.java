package com.projetenchere.common.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;

public class ClientAcceptor<T extends Serializable> extends Thread {
    private final Map<Headers, IDataHandler> handlers;
    private ObjectInputStream objectInput;
    private ObjectOutputStream objectOutput;

    public synchronized void addHandler(Headers header, IDataHandler replyer) {
        this.handlers.put(header, replyer);
    }

    public synchronized void removeHandler(Headers header) {
        this.handlers.remove(header);
    }

    public ClientAcceptor(
            Map<Headers, IDataHandler> handlers,
            ObjectInputStream objectInputStream,
            ObjectOutputStream objectOutputStream) {
        this.handlers = handlers;
        this.objectInput = objectInputStream;
        this.objectOutput = objectOutputStream;
    }


    @Override
    public void run() {
        try {
            while (true) {
                checkStreams();
                Object rawRequest = this.getObjectInput().readObject();
                DataWrapper<T> dataInput = (DataWrapper<T>) rawRequest;
                System.out.println("Received " + dataInput.getHeader());
                T object = dataInput.unwrap();
                DataWrapper<? extends Serializable> dataOutput;
                if (handlers.containsKey(dataInput.getHeader())) {
                    dataOutput = handlers.get(dataInput.getHeader()).handle(object);
                    this.getObjectOutput().writeObject(dataOutput);
                }
            }
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            throw new RuntimeException(e);
        }
    }

    public ObjectInputStream getObjectInput() {
        return objectInput;
    }

    public ObjectOutputStream getObjectOutput() {
        return objectOutput;
    }

    public void checkStreams() {
        if (objectOutput == null || objectInput == null) throw new RuntimeException("No streams for IO");
    }

    public void setInputStream(ObjectInputStream inputStream) {
        this.objectInput = inputStream;
    }

}
