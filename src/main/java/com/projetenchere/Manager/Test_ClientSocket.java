package com.projetenchere.Manager;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Test_ClientSocket {
    public static void main(String[] args) throws IOException {
        String serverAddress = "172.16.214.1";
        int serverPort = 12345;
        Socket clientSocket = new Socket(serverAddress, serverPort);

        OutputStream output = clientSocket.getOutputStream();
        String message = "Bonjour, serveur!";
        byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
        output.write(messageBytes);
        output.flush();
        output.close();

        clientSocket.close();
    }
}
