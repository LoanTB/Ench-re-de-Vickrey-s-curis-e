package com.projetenchere.Bidder.Controller.network;

import com.projetenchere.common.Model.BidStarter;
import com.projetenchere.common.network.NetworkUtil;
import com.projetenchere.common.network.ObjectSender;

import java.io.IOException;
import java.net.UnknownHostException;

public class BidderNetworkController {
    private static final String MANAGER_ADDRESS = "localhost";
    private static final int MANAGER_PORT = 24681;

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
        return (BidStarter)
    }


}
