package com.projetenchere.Manager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.Serializable;

public class Test_ClientSocket {
    public static void main(String[] args) throws IOException {
        try {
            String serverAddress = "172.16.214.1";
            int serverPort = 12345;
            Socket clientSocket = new Socket(serverAddress, serverPort);

            OutputStream output = clientSocket.getOutputStream();
            ObjectOutputStream objectOutput = new ObjectOutputStream(output);

            Test_ObjetTransiter objetEnvoi = new Test_ObjetTransiter(1, "C'est l'objet avec un message");


            objectOutput.writeObject(objetEnvoi);
            objectOutput.flush();
            objectOutput.close();

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
