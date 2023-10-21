package com.projetenchere.Manager.Controller;

import com.projetenchere.common.Model.Bid;
import com.projetenchere.common.Model.BidStarter;
import com.projetenchere.common.Model.Encrypted.EncryptedPrices;
import com.projetenchere.common.Model.Winner;
import com.projetenchere.common.network.NetworkUtil;
import com.projetenchere.common.network.ObjectSender;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.PublicKey;
import java.util.Set;
import java.util.HashSet;

public class ManagerNetworkController {
    private String ManagerIp;
    private int ManagerPort = 2463;

    public String getManagerIp() {
        return ManagerIp;
    }

    public void setManagerIp(String managerIp) {
        ManagerIp = managerIp;
    }

    public ManagerNetworkController() throws UnknownHostException {
        String ManagerIp = NetworkUtil.getMyIP();
        setManagerIp(ManagerIp);
    }

    public void waitAskInitPackByBidder(BidStarter currentBidStarter) throws IOException, ClassNotFoundException {
        Bid currentBid = currentBidStarter.getCurrentBid();
        while (!currentBid.isOver()) {
            ObjectSender request = NetworkUtil.receive(ManagerPort);
            if (request.getObject() == "getBidderInitPackage") {
                sendBidAndKey(currentBidStarter);
            }
        }
    }

    public void sendBidAndKey(BidStarter currentStarter) throws IOException {
        ObjectSender pack = new ObjectSender(ManagerIp,ManagerPort,currentStarter,BidStarter.class);
        NetworkUtil.send(ManagerIp,ManagerPort,pack);
    }

    public EncryptedPrices fetchEncryptedPrice() throws IOException, ClassNotFoundException {
        ObjectSender request = NetworkUtil.receive(ManagerPort);
        EncryptedPrices pack = (EncryptedPrices)request.getObjectClass().cast(request.getObject());
        return pack;
    }

    public void sendWinnerAndPrice(String sellerAddress,Winner result) throws IOException {
        ObjectSender pack = new ObjectSender(ManagerIp,ManagerPort,result,Winner.class);
        NetworkUtil.send(sellerAddress,ManagerPort,pack); //ATTENTION Destiné au seller. ip à changer à l'avenir.
    }

}