package com.projetenchere.Manager.Controller;

import com.projetenchere.common.Model.Bid;
import com.projetenchere.common.Model.BidStarter;
import com.projetenchere.common.Model.Encrypted.EncryptedPrices;
import com.projetenchere.common.Model.Serializable.ObjectSender;
import com.projetenchere.common.Model.Winner;
import com.projetenchere.common.Util.NetworkUtil;

import java.io.IOException;
import java.net.UnknownHostException;

public class ManagerNetworkController {
    final private String ManagerIp;
    final private int ManagerPort = 2463;
    private String sellerAddress;
    private int SellerPort = 24682;

    public String getManagerIp() {
        return ManagerIp;
    }

    public int getManagerPort() {
        return ManagerPort;
    }

    public String getSellerAddress() {
        return sellerAddress;
    }

    public void setSellerAddress(String sellerAddress) {
        this.sellerAddress = sellerAddress;
    }

    public void setSellerPort(int sellerPort) {
        SellerPort = sellerPort;
    }

    public int getSellerPort() {
        return SellerPort;
    }

    public ManagerNetworkController() throws UnknownHostException {
        ManagerIp = NetworkUtil.getMyIP();
    }

    public void sendBidToSeller(Bid currentBid) throws IOException {
        ObjectSender pack = new ObjectSender(getManagerIp(), getManagerPort(), currentBid, Bid.class);
        NetworkUtil.send(getSellerAddress(), getSellerPort(), pack);
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
        ObjectSender pack = new ObjectSender(getManagerIp(), getManagerPort(), currentStarter, BidStarter.class);
        NetworkUtil.send(getManagerIp(), getManagerPort(), pack);
    }

    public EncryptedPrices fetchEncryptedPrice() throws IOException, ClassNotFoundException {
        ObjectSender request = NetworkUtil.receive(getManagerPort());
        EncryptedPrices pack = (EncryptedPrices) request.getObjectClass().cast(request.getObject());
        return pack;
    }

    public void sendWinnerAndPrice(Winner result) throws IOException {
        ObjectSender pack = new ObjectSender(getManagerIp(), getManagerPort(), result, Winner.class);
        NetworkUtil.send(getSellerAddress(), getSellerPort(), pack);
    }

}