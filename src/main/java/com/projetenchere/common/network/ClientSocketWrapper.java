package com.projetenchere.common.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientSocketWrapper {

    private final Socket s;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    public ClientSocketWrapper(Socket s) {
        this.s = s;
    }

    public ObjectOutputStream getObjectOutputStream() {
        if (objectOutputStream == null) {
            try {
                objectOutputStream = new ObjectOutputStream(s.getOutputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return objectOutputStream;
    }

    public ObjectInputStream getObjectInputStream() {
        if (objectInputStream == null) {
            try {
                objectInputStream = new ObjectInputStream(s.getInputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return objectInputStream;
    }
}
