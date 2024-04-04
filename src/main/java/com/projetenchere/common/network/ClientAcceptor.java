package com.projetenchere.common.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;

public class ClientAcceptor<T extends Serializable> extends Thread {
    private final Map<Headers, IDataHandler> handlers;
    private final ObjectOutputStream objectOutput;
    private final Server owner;
    boolean stop = false;
    private ObjectInputStream objectInput;

    public ClientAcceptor(Map<Headers, IDataHandler> handlers, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream, Server owner) {
        this.handlers = handlers;
        this.objectInput = objectInputStream;
        this.objectOutput = objectOutputStream;
        this.owner = owner;
    }

    public synchronized void addHandler(Headers header, IDataHandler replyer) {
        this.handlers.put(header, replyer);
    }

    public synchronized void removeHandler(Headers header) {
        this.handlers.remove(header);
    }

    private void cleanup() {
        if (this.objectOutput != null) {
            try {
                this.objectOutput.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (this.objectInput != null) {
            try {
                this.objectInput.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        this.owner.stopConnection(this);
    }

    @Override
    public void run() {
        try {
            while (!stop) {
                checkStreams();
                Object rawRequest = this.getObjectInput().readObject();
                DataWrapper<T> dataInput = (DataWrapper<T>) rawRequest;
                T object = dataInput.unwrap();
                DataWrapper<? extends Serializable> dataOutput;
                if (dataInput.checkHeader(Headers.GOODBYE_HAVE_A_NICE_DAY)) {
                    this.stop = true;
                } else if (handlers.containsKey(dataInput.getHeader())) {
                    System.out.println("Received " + dataInput.getHeader());
//                    System.out.println(handlers);
                    dataOutput = handlers.get(dataInput.getHeader()).handle(object);
                    System.out.println("Sent " + dataOutput.getHeader());
                    this.getObjectOutput().writeObject(dataOutput);
                }
            }
        } catch (ClassNotFoundException | ClassCastException e) {
            throw new RuntimeException(e);
        } catch (IOException i) {
            System.out.println("Unexpected closure");
        }
        this.cleanup();
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
}
