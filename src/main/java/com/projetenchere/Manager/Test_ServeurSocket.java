

package com.projetenchere.Manager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.Serializable;

public class Test_ServeurSocket {
    public static void main(String[] args) throws IOException {
        try {
            int port = 12345;
            ServerSocket serverSocket = new ServerSocket(port);
            Socket clientSocket = serverSocket.accept();
            System.out.println("Attente de connexions entrantes...");

            InputStream input = clientSocket.getInputStream();
            ObjectInputStream objectInput = new ObjectInputStream(input);
            System.out.println("Connexion établie avec le client.");

            Test_ObjetTransiter objetRecu = (Test_ObjetTransiter)objectInput.readObject();

            System.out.println("Objet reçu : " + objetRecu.getMessage());

            clientSocket.close();
            serverSocket.close();
            System.out.println("Ca passe");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
