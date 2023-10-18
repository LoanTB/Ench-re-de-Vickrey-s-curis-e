package com.projetenchere.Manager;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Test_ServeurSocket {
    public static void main(String[] args) throws IOException {
        try {
            //Test_ObjetTransiterBis objetRecu = (Test_ObjetTransiterBis) recevoirObjet(12345, Test_ObjetTransiterBis.class.getName());
            int objetRecu = (int) recevoirObjet(12345, Integer.class.getName());
            System.out.println("Objet reçu : " + objetRecu);

        } catch (ArithmeticException e) {
            e.printStackTrace();
        }
    }

    public static <myClass> Object recevoirObjet(int port, String className_DonnerPara) throws IOException {
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
                // Erreur car on appel un objet qui n'est pas de même type que celui renvoyer
                throw new ArithmeticException("Type transmit entre client/serveur est different");
            }

            // On veux récupère l'objet
            Class<?> myClass = Class.forName(className_RecupClient);
            myClass objetRecu = (myClass) objectInput.readObject();

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
}
