package com.projetenchere.Manager.Controller;

import com.projetenchere.common.Model.Bid;
import com.projetenchere.common.Model.BidStarter;
import com.projetenchere.common.Model.Encrypted.EncryptedPrices;
import com.projetenchere.common.Model.Winner;
import com.projetenchere.common.network.NetworkUtil;
import com.projetenchere.common.network.ObjectSender;

import java.io.IOException;
import java.net.UnknownHostException;

public class ManagerNetworkController {
    final private String ManagerIp;
    final private int ManagerPort = 2463;

    public String getManagerIp() {
        return ManagerIp;
    }

    public int getManagerPort() {
        return ManagerPort;
    }

    public ManagerNetworkController() throws UnknownHostException {
        ManagerIp = NetworkUtil.getMyIP();
    }

    public void waitAskInitPackByBidder(BidStarter currentBidStarter) throws IOException, ClassNotFoundException {
        Bid currentBid = currentBidStarter.getCurrentBid();
        while (!currentBid.isOver()) {
            ObjectSender request = NetworkUtil.receive(getManagerPort());
            if (request.getObject() == "getBidderInitPackage") {
                sendBidAndKey(currentBidStarter);
            }
        }
    }

    public void sendBidAndKey(BidStarter currentStarter) throws IOException {
        ObjectSender pack = new ObjectSender(getManagerIp(),getManagerPort(),currentStarter,BidStarter.class);
        NetworkUtil.send(getManagerIp(),getManagerPort(),pack);
    }

    public EncryptedPrices fetchEncryptedPrice() throws IOException, ClassNotFoundException {
        ObjectSender request = NetworkUtil.receive(getManagerPort());
        EncryptedPrices pack = (EncryptedPrices)request.getObjectClass().cast(request.getObject());
        return pack;
    }

    public void sendWinnerAndPrice(String sellerAddress,Winner result) throws IOException {
        ObjectSender pack = new ObjectSender(getManagerIp(),getManagerPort(),result,Winner.class);
        NetworkUtil.send(sellerAddress,getManagerPort(),pack); //ATTENTION Destiné au seller. ip à changer à l'avenir.
    }

}