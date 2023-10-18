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
        try {
            String serverAddress = "172.16.214.1";
            int serverPort = 12345;
            Socket clientSocket = new Socket(serverAddress, serverPort);
            OutputStream output = clientSocket.getOutputStream();
            ObjectOutputStream objectOutput = new ObjectOutputStream(output);

            // Envoie du type
            Test_ObjetTransiterBis objetEnvoi = new Test_ObjetTransiterBis("le signal", new int[] {1,2});
            String firstString = objetEnvoi.getClass().getName();
            objectOutput.writeObject(firstString);
            objectOutput.flush();

            // Envoie de l'objet
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
