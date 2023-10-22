package com.projetenchere.Bidder.Controller.network;

import com.projetenchere.common.Model.BidStarter;
import com.projetenchere.common.Model.Offer;
import com.projetenchere.common.Model.Serializable.ObjectSender;
import com.projetenchere.common.Util.NetworkUtil;

import java.io.IOException;
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
                NetworkUtil.getMyIP(),
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

    public void sendOffer(Offer offer, String sellerIP) throws IOException {
        ObjectSender objectSender = new ObjectSender(
                NetworkUtil.getMyIP(),
                24681,
                offer,
                offer.getClass());
        NetworkUtil.send(sellerIP, 24682, objectSender);
    }

    public int fetchPrice() throws IOException, ClassNotFoundException {
        ObjectSender receiver = NetworkUtil.receive(24682);
        if (!receiver.getObjectClass().equals(int.class)) {
            throw new ClassNotFoundException("Did not receive the required class");
        } else {
            return (int) receiver.getObject();
        }
    }



}
