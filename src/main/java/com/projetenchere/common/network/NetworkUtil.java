package com.projetenchere.common.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class NetworkUtil {

    public static Object receive(int port) throws IOException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket(port);
        Socket clientSocket = serverSocket.accept();
        ObjectInputStream objectInput = new ObjectInputStream(clientSocket.getInputStream());
        return (Object) objectInput.readObject();
    }

    public static void send(String serverAddress, int serverPort, Object data) throws IOException {
        Socket clientSocket = new Socket(serverAddress, serverPort);
        ObjectOutputStream objectOutput = new ObjectOutputStream(clientSocket.getOutputStream());
        objectOutput.writeObject(data);
        objectOutput.flush();
    }

    public static String getMyIP() throws UnknownHostException {
        InetAddress localHost = InetAddress.getLocalHost();
        return localHost.getHostAddress();
    }
}
