package com.projetenchere.common.Utils;

import com.projetenchere.common.network.DataWrapper;
import com.projetenchere.common.network.Headers;

import java.io.*;
import java.net.Socket;

public class SerializationUtil {
    public static void serializeTo(DataWrapper<?> data, Socket socket) throws IOException {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(data);
    }
    public static <T extends Serializable> DataWrapper<T> deserialize(Socket socket) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        Object rawData = objectInputStream.readObject();
        DataWrapper<T> data;
        try {
            data = (DataWrapper<T>) rawData;
        } catch (ClassCastException e) {
            data = new DataWrapper<>(null, Headers.ERROR);
        }
        return data;
    }
}
