package com.projetenchere.Bidder.Controller.network;

import com.projetenchere.common.Model.BidStarter;
import com.projetenchere.common.Model.Encrypted.EncryptedOffer;
import com.projetenchere.common.Model.Offer;
import com.projetenchere.common.Model.Sendable.ObjectSender;
import com.projetenchere.common.Util.NetworkUtil;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class BidderNetworkController {
    private static final String MANAGER_ADDRESS = "127.0.0.1";
    private static final int MANAGER_PORT = 24683;

    private String myIp() {
        try {
            return NetworkUtil.getMyIP();
        } catch (UnknownHostException e) {
            return "localhost";
        }
    }

    public BidStarter askForInitPackage() throws IOException, ClassNotFoundException {
        String greet = "getBidderInitPackage";
        ObjectSender objectSender = new ObjectSender(
                myIp(),
                24681,
                greet,
                greet.getClass());
        NetworkUtil.send(MANAGER_ADDRESS, MANAGER_PORT, objectSender);
        ObjectSender receiver = NetworkUtil.receive(24681);
        if (!receiver.getObjectClass().equals(BidStarter.class)) {
            throw new ClassNotFoundException("Received wrong class");
        } else {
            return (BidStarter) receiver.getObject();
        }
    }

    public void sendOffer(EncryptedOffer offer, String sellerIP) throws IOException {
        ObjectSender objectSender = new ObjectSender(
                myIp(),
                24681,
                offer,
                offer.getClass());
        NetworkUtil.send(sellerIP, 24682, objectSender);
    }

    public double fetchPrice() throws IOException, ClassNotFoundException {
        while (true) {
            try {
                ObjectSender receiver = NetworkUtil.receive(24681);
                if (!receiver.getObjectClass().equals(Double.class)) {
                    System.out.println(receiver.getObjectClass());
                    throw new ClassNotFoundException("Did not receive the required class");
                } else {
                    return (Double) receiver.getObject();
                }
            } catch (SocketTimeoutException ignored){}
        }
    }



}
