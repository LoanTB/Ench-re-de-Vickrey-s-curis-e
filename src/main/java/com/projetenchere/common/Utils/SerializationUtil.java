package com.projetenchere.common.Utils;

import com.projetenchere.common.network.DataWrapper;
import com.projetenchere.common.network.NetworkData;

import javax.net.ssl.SSLSocket;
import java.io.*;

public class SerializationUtil {
    public static void serializeTo(NetworkData data, SSLSocket socket) throws IOException {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(data);
    }
    public static NetworkData deserializeAsNetworkDataFrom(SSLSocket socket) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        Object rawData = objectInputStream.readObject();
        NetworkData data;
        try {
            data = (NetworkData) rawData;
        } catch (ClassCastException e) {
            data = NetworkData.error();
        }
        return data;
    }

    public static <T extends Serializable> DataWrapper<T> deserializeAsDataWrapper(SSLSocket socket) throws IOException, ClassNotFoundException {
        NetworkData data = deserializeAsNetworkDataFrom(socket);
        try {
            return (DataWrapper<T>) data;
        } catch (ClassCastException e) {
            throw new RuntimeException("Could not cast to data");
        }
    }

}
