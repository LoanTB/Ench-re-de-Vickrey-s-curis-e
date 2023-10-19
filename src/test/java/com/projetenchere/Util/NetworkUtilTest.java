package com.projetenchere.Util;

import com.projetenchere.common.Util.NetworkUtil;
import org.junit.jupiter.api.Test;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NetworkUtilTest {

    Thread serverThread;
    Thread clientThread;

    @Test
    public void test_basic_string_transmission() throws InterruptedException {
        final String[] data = new String[2]; // Pour avoir de la mémoire partagé avec les threads

        serverThread = new Thread(() -> { // Thread serveur
            try {
                data[0] =  new String("Je suis un test");
                NetworkUtil.envoiObjet(NetworkUtil.getMyIP(), 24681,data[0]);
            } catch (IOException e) {
                throw new RuntimeException("Erreur côté serveur: " + e);
            }
        });

        clientThread = new Thread(() -> { // Thread client
            try {
                data[1] = NetworkUtil.recevoirObjet(24681,String.class);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException("Erreur côté client: " + e);
            }
        });

        serverThread.start(); // Démarrer le serveur
        clientThread.start(); // Démarrer le client

        serverThread.join(); // Attendre serveur
        clientThread.join(); // Attendre client

        assertEquals(data[0], data[1]);
    }
}