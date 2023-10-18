package com.projetenchere.Manager;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class NetworkSpace {
    public static <myClass> Object recevoirObjet(int port, String className_DonnerPara) {
        try {
            // Création du serveur et attente/connexion avec le client
            ServerSocket serverSocket = new ServerSocket(port);
            Socket clientSocket = serverSocket.accept();
            InputStream input = clientSocket.getInputStream();
            ObjectInputStream objectInput = new ObjectInputStream(input);

            // On verrifie que l'objet attendue est le même que celui envoyer
            // Recupération de la className_RecupClient
            String className_RecupClient = (String) objectInput.readObject();
            System.out.println(className_DonnerPara);
            if (!className_RecupClient.equals(className_DonnerPara)){
                throw new ArithmeticException("Type transmit entre client/serveur est different");
            }

            // On veux récupère l'objet
            Class<?> myClass = Class.forName(className_RecupClient);
            myClass objetRecu = (myClass) objectInput.readObject();

            // On ferme le socket et la connexion
            objectInput.close();
            input.close();
            clientSocket.close();
            serverSocket.close();
            return objetRecu;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new ArithmeticException("Fonction 'recevoirObjet' mal terminer");
    }

    public static void envoiObjet(String serverAddress, int serverPort, Object objetEnvoi) {
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

            // On ferme le socket et la connexion
            objectOutput.close();
            output.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void getMyIP() {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            String ipAddress = localHost.getHostAddress();
            System.out.println("Adresse IP de la machine locale : " + ipAddress);
        } catch ( UnknownHostException e) {
            System.err.println("Impossible de récupérer l'adresse IP : " + e.getMessage());
        }
    }
}
