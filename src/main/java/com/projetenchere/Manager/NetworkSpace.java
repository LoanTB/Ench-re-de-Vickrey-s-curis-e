package com.projetenchere.Manager;

import java.io.*;
import java.net.*;

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
        throw new ArithmeticException("Fonction 'recevoirObjet' c'est mal terminer");
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


    public static String getMyIP() {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            return localHost.getHostAddress();
        } catch ( UnknownHostException e) {
            System.err.println("Impossible de récupérer l'adresse IP : " + e.getMessage());
        }
        throw new ArithmeticException("Fonction 'getMyIP' c'est mal terminer");
    }

    /*public static boolean testPort(String serverHostname, int serverPort) {
        try {
            InetAddress serverAddress = InetAddress.getByName(serverHostname);
            InetSocketAddress serverSocketAddress = new InetSocketAddress(serverAddress, serverPort);

            Socket socket = new Socket();
            socket.connect(serverSocketAddress, 3000);
            socket.close();

            return true;
        } catch (IOException e) {
            System.err.println("Impossible de récupérer le port du serveur distant : " + e.getMessage());
        }
        throw new ArithmeticException("Fonction 'testPort' c'est mal terminer");
    }*/
}
