package com.projetenchere.Seller.Controller;

import com.projetenchere.common.Models.Bid;
import com.projetenchere.common.Models.Encrypted.EncryptedOffer;
import com.projetenchere.common.Models.Offer;
import com.projetenchere.common.Models.Network.Sendable.ObjectSender;
import com.projetenchere.common.Utils.EncryptionUtil;
import com.projetenchere.common.Utils.Network.NetworkUtil;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.UnknownHostException;
import java.security.KeyPair;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SellerControllerTest {

    Thread serverThread;
    Thread clientThread;

    final String localhost = "127.0.0.1";

    final int BidderPort = 24681;
    final int SellerPort = 24682;
    final int managerPort = 24683;

    @Test
    public void test_fetchCurrentBid() throws InterruptedException, UnknownHostException {
        SellerController controller = new SellerController();

        serverThread = new Thread(() -> {
            Bid data = new Bid("TEST","TEST", LocalDateTime.parse("22-10-2023 16:00:00", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
            ObjectSender objectSender = new ObjectSender(localhost,SellerPort,data,data.getClass());
            try {
                NetworkUtil.send(localhost,SellerPort,objectSender);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        clientThread = new Thread(() -> {
            controller.fetchCurrentBid();
        });

        serverThread.start();
        clientThread.start();

        serverThread.join();
        clientThread.join();

        assertEquals(controller.getCurrentBid().getDescription(),"TEST");
    }

    @Test
    public void test_receiveOffersUntilBidEnd() throws InterruptedException, UnknownHostException {
        SellerController controller = new SellerController();

        serverThread = new Thread(() -> {
            Bid bid = new Bid("TEST", "TEST", LocalDateTime.now().plusSeconds(5));
            ObjectSender objectSender = new ObjectSender(localhost, SellerPort, bid, bid.getClass());
            try {
                NetworkUtil.send(localhost, SellerPort, objectSender);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            KeyPair managerKeys = null;
            try {
                managerKeys = EncryptionUtil.generateKeyPair();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            PublicKey manager_publicKey = managerKeys.getPublic();
            Offer offer = new Offer("Pablo", 10.0);
            EncryptedOffer encryptedOffer;
            try {
                encryptedOffer = new EncryptedOffer(offer, manager_publicKey);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            objectSender = new ObjectSender(localhost, SellerPort, encryptedOffer, encryptedOffer.getClass());
            try {
                NetworkUtil.send(localhost, SellerPort, objectSender);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        clientThread = new Thread(() -> {
            controller.fetchCurrentBid();
            controller.receiveOffersUntilBidEnd();
        });

        serverThread.start();
        clientThread.start();

        serverThread.join();
        clientThread.join();

        assertEquals(controller.getCurrentBid().getDescription(),"TEST");
    }
}