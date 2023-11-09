package com.projetenchere.Manager.Controller;

import com.projetenchere.common.Model.Bid;
import com.projetenchere.common.Model.BidStarter;
import com.projetenchere.common.Model.Encrypted.EncryptedPrices;
import com.projetenchere.common.Model.Sendable.ObjectSender;
import com.projetenchere.common.Model.Winner;
import com.projetenchere.common.Utils.NetworkUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import static java.lang.Thread.sleep;

public class ManagerNetworkController {
    final private String managerIp;
    final private int managerPort = 24683;
    private String sellerAddress;
    private final int sellerPort = 24682;

    public ManagerNetworkController() throws UnknownHostException {
        managerIp = NetworkUtils.getMyIP();
    }

    public String getManagerIp() {
        return managerIp;
    }

    public int getManagerPort() {
        return managerPort;
    }

    public String getSellerAddress() {
        return sellerAddress;
    }

    public void setSellerAddress(String sellerAddress) {
        this.sellerAddress = sellerAddress;
    }

    public int getSellerPort() {
        return sellerPort;
    }

    public void setSellerPort(int sellerPort) {
        sellerPort = sellerPort;
    }

    public void sendBidToSeller(Bid currentBid) throws IOException {
        ObjectSender pack = new ObjectSender(getManagerIp(), getManagerPort(), currentBid, Bid.class);
        NetworkUtils.send(getSellerAddress(), getSellerPort(), pack);
    }

    public void waitAskInitPackByBidder(BidStarter currentBidStarter) throws IOException, ClassNotFoundException, InterruptedException {
        Bid currentBid = currentBidStarter.getCurrentBid();
        while (!currentBid.isOver()) {
            try {
                ObjectSender request = NetworkUtils.receive(getManagerPort(),2000);
                if ((request.getObject()).equals("getBidderInitPackage")) {
                    sleep(100);
                    sendBidAndKey(currentBidStarter, request.getPORT_sender(), request.getIP_sender());
                }
            } catch (SocketTimeoutException ignored) {
            }
        }
    }

    public void sendBidAndKey(BidStarter currentStarter, int portSender, String ipSender) throws IOException {
        ObjectSender pack = new ObjectSender(getManagerIp(), getManagerPort(), currentStarter, BidStarter.class);
        NetworkUtils.send(ipSender, portSender, pack);
    }

    public EncryptedPrices fetchEncryptedPrice() throws IOException, ClassNotFoundException {
        EncryptedPrices pack = null;
        while (pack == null){
            ObjectSender request = NetworkUtils.receive(getManagerPort(),0);
            pack = (EncryptedPrices) request.getObjectClass().cast(request.getObject());
        }
        return pack;
    }

    public void sendWinnerAndPrice(Winner result) throws IOException {
        ObjectSender pack = new ObjectSender(getManagerIp(), getManagerPort(), result, Winner.class);
        NetworkUtils.send(getSellerAddress(), getSellerPort(), pack);
    }

}