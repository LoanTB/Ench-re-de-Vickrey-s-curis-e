package com.projetenchere.Seller.Controller;

import com.projetenchere.common.Model.Bid;
import com.projetenchere.common.Model.Sendable.ObjectSender;
import com.projetenchere.common.Util.NetworkUtil;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SellerControllerTest {

    Thread serverThread;
    Thread clientThread;

    @Test
    public void test_fetchCurrentBid() throws InterruptedException, UnknownHostException {
        SellerController controller = new SellerController();
        String SellerIp = NetworkUtil.getMyIP();
        int SellerPort = 24682;


        serverThread = new Thread(() -> {
            Bid data = new Bid("TEST","TEST", LocalDateTime.parse("22-10-2023 16:00:00", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
            ObjectSender objectSender = new ObjectSender(SellerIp,SellerPort,data,data.getClass());
            try {
                NetworkUtil.send(SellerIp,SellerPort,objectSender);
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

        assertEquals(controller.getCurrentBid().getBidDescription(),"TEST");
    }
}