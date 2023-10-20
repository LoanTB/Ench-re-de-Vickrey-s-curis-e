package com.projetenchere.common;

import com.projetenchere.common.network.NetworkUtil;
import com.projetenchere.common.network.ObjectSender;
import org.junit.jupiter.api.Test;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NetworkUtilTest {

    Thread serverThread;
    Thread clientThread;

    @Test
    public void test_ObjectSender_transmission() throws InterruptedException {
        final ObjectSender[] data = new ObjectSender[2];

        serverThread = new Thread(() -> {
            try {
                data[0] =  new ObjectSender(NetworkUtil.getMyIP(),24681,new String("Super message !"),String.class);
                NetworkUtil.send(NetworkUtil.getMyIP(), 24681,data[0]);
            } catch (IOException e) {
                throw new RuntimeException("Erreur côté serveur: " + e);
            }
        });

        clientThread = new Thread(() -> {
            try {
                data[1] = (ObjectSender) NetworkUtil.receive(24681);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException("Erreur côté client: " + e);
            }
        });

        serverThread.start();
        clientThread.start();

        serverThread.join();
        clientThread.join();

        assertEquals(data[0].getObjectClass().cast(data[0].getObject()),data[1].getObjectClass().cast(data[1].getObject()));

        serverThread = new Thread(() -> {
            try {
                data[0] =  new ObjectSender(NetworkUtil.getMyIP(),24681,new String("Oui !"),String.class);
                NetworkUtil.send(data[1].getIP_serder(),data[1].getPORT_sender(),data[0]);
            } catch (IOException e) {
                throw new RuntimeException("Erreur côté serveur: " + e);
            }
        });

        clientThread = new Thread(() -> {
            try {
                data[1] = (ObjectSender) NetworkUtil.receive(data[1].getPORT_sender());
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException("Erreur côté client: " + e);
            }
        });

        serverThread.start();
        clientThread.start();

        serverThread.join();
        clientThread.join();

        assertEquals(data[1].getObjectClass().cast(data[1].getObject()),"Oui !");
    }
}