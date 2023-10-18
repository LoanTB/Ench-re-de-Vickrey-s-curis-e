package com.projetenchere.Manager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

public class Test_ClientSocket {
    public static void main(String[] args) throws IOException {
        String serverAddress = "172.16.214.1";
        int serverPort = 12345;
        Test_ObjetTransiterBis objetEnvoi = new Test_ObjetTransiterBis("le signal", new int[] {1,2});
        int a = 1;
        envoiObjet(serverAddress, serverPort, a);
    }

    public static void envoiObjet(String serverAddress, int serverPort, Object objetEnvoi) throws IOException {
        try {
            // Connexion au serveur
            Socket clientSocket = new Socket(serverAddress, serverPort);
            OutputStream output = clientSocket.getOutputStream();
            ObjectOutputStream objectOutput = new ObjectOutputStream(output);

            // Envoi du type de l'objet
            String className_Envoi = objetEnvoi.getClass().getName();
            System.out.println(className_Envoi);
            objectOutput.writeObject(className_Envoi);
            objectOutput.flush();

            // Envoi de l'objet
            objectOutput.writeObject(objetEnvoi);
            objectOutput.flush();

            objectOutput.close();
            output.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
