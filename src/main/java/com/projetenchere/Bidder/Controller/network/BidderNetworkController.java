package com.projetenchere.Bidder.Controller.network;

import com.projetenchere.common.Model.BidStarter;
import com.projetenchere.common.Model.Encrypted.EncryptedOffer;
import com.projetenchere.common.Model.Sendable.ObjectSender;
import com.projetenchere.common.Util.NetworkUtils;

import java.io.IOException;
import java.net.UnknownHostException;

public class BidderNetworkController {
    private static final String MANAGER_ADDRESS = "127.0.0.1";
    private static final int MANAGER_PORT = 24683;

    private String myIp() {
        try {
            return NetworkUtils.getMyIP();
        } catch (UnknownHostException e) {
            return "localhost";
        }
    }

    public BidStarter askForInitPackage(int localPort) throws IOException, ClassNotFoundException {
        String greet = "getBidderInitPackage";
        ObjectSender objectSender = new ObjectSender(
                myIp(),
                localPort,
                greet,
                greet.getClass());
        NetworkUtils.send(MANAGER_ADDRESS, MANAGER_PORT, objectSender);
        ObjectSender receiver = NetworkUtils.receive(localPort,30000);
        if (!receiver.getObjectClass().equals(BidStarter.class)) {
            throw new ClassNotFoundException("Received wrong class");
        } else {
            return (BidStarter) receiver.getObject();
        }
    }

    public void sendOffer(EncryptedOffer offer, String sellerIP, int localPort) throws IOException {
        ObjectSender objectSender = new ObjectSender(
                myIp(),
                localPort,
                offer,
                offer.getClass());
        NetworkUtils.send(sellerIP, 24682, objectSender);
    }

    public double fetchPrice(int localPort) throws IOException, ClassNotFoundException {
        ObjectSender receiver = NetworkUtils.receive(localPort,0);
        if (!receiver.getObjectClass().equals(Double.class)) {
            System.out.println(receiver.getObjectClass());
            throw new ClassNotFoundException("Did not receive the required class");
        } else {
            return (Double) receiver.getObject();
        }
    }



}
