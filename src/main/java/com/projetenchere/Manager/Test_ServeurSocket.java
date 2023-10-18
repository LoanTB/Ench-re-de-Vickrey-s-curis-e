

package com.projetenchere.Manager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Test_ServeurSocket {
    public static void main(String[] args) throws IOException {
        int port = 12345;
        ServerSocket serverSocket = new ServerSocket(port);
        Socket clientSocket = serverSocket.accept();
        System.out.println("Attente de connexions entrantes...");

        InputStream input = clientSocket.getInputStream();
        System.out.println("Connexion établie avec le client.");

        byte[] buffer = new byte[1024];
        int bytesRead;
        StringBuilder messageBuilder = new StringBuilder();
        while ((bytesRead = input.read(buffer)) != -1) {
            String messagePart = new String(buffer, 0, bytesRead, StandardCharsets.UTF_8);
            messageBuilder.append(messagePart);
        }
        String message = messageBuilder.toString();
        System.out.println("Message reçu du client : " + message);

        //OutputStream output = clientSocket.getOutputStream();

        clientSocket.close();
        serverSocket.close();
        System.out.println("Ca passe");
    }
}
