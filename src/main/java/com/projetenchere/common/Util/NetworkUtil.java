package com.projetenchere.common.Util;

import com.projetenchere.common.Model.Serializable.ObjectSender;

import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

public class NetworkUtil {

    public static ObjectSender receive(int port) throws IOException, ClassNotFoundException, InvalidClassException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            serverSocket.setSoTimeout(60000);// 1 minute
            try (Socket clientSocket = serverSocket.accept();
                 ObjectInputStream objectInput = new ObjectInputStream(clientSocket.getInputStream())) {

                Object data = objectInput.readObject();
                if (data instanceof ObjectSender) {
                    return (ObjectSender) data;
                } else {
                    throw new InvalidClassException("Received object is not an instance of ObjectSender");
                }
            } catch (SocketTimeoutException e) {
                throw new SocketTimeoutException("Timeout: No connection was established within the timeout.");
            }
        }
    }


    public static void send(String serverAddress, int serverPort, ObjectSender data) throws IOException {
        try (Socket clientSocket = new Socket(serverAddress, serverPort);
             ObjectOutputStream objectOutput = new ObjectOutputStream(clientSocket.getOutputStream())) {
            objectOutput.writeObject(data);
            objectOutput.flush();
        }
    }

    public static String getMyIP() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }
}
