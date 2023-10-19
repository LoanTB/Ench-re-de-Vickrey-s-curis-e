package com.projetenchere.common.Util;

import java.io.*;
import java.net.*;

public class NetworkUtil {

    public static <T> T recevoirObjet(int port, Class<T> expectedClass) throws IOException, ClassNotFoundException {
        try (ServerSocket serverSocket = new ServerSocket(port);
             Socket clientSocket = serverSocket.accept();
             ObjectInputStream objectInput = new ObjectInputStream(clientSocket.getInputStream())) {

            // Récupération du nom de la classe
            String className_RecupClient = (String) objectInput.readObject();

            // Vérification du nom de la classe
            if (!className_RecupClient.equals(expectedClass.getName())) {
                throw new IllegalArgumentException("Le type d'objet reçu est différent du type attendu");
            }

            // Récupération de l'objet
            return expectedClass.cast(objectInput.readObject());
        }
    }

    public static void envoiObjet(String serverAddress, int serverPort, Object objetEnvoi) throws IOException {
        try (Socket clientSocket = new Socket(serverAddress, serverPort);
             ObjectOutputStream objectOutput = new ObjectOutputStream(clientSocket.getOutputStream())) {

            // Envoi du type de l'objet
            objectOutput.writeObject(objetEnvoi.getClass().getName());
            objectOutput.flush();

            // Envoi de l'objet
            objectOutput.writeObject(objetEnvoi);
            objectOutput.flush();
        }
    }

    public static String getMyIP() throws UnknownHostException {
        InetAddress localHost = InetAddress.getLocalHost();
        return localHost.getHostAddress();
    }
}
