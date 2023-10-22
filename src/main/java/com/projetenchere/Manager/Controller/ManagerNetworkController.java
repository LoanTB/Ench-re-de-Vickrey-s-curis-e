package com.projetenchere.Manager.Controller;

import com.projetenchere.common.Model.Bid;
import com.projetenchere.common.Model.BidStarter;
import com.projetenchere.common.Model.Encrypted.EncryptedPrices;
import com.projetenchere.common.Model.Sendable.ObjectSender;
import com.projetenchere.common.Model.Winner;
import com.projetenchere.common.Util.NetworkUtil;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import static java.lang.Thread.sleep;

public class ManagerNetworkController {
    final private String managerIp;
    final private int managerPort = 24683;
    private String sellerAddress;
    private int sellerPort = 24682;
   // private int bidderPort = 24681;
/*
    public int getBidderPort() {
        return bidderPort;
    }
 */
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

    public void setSellerPort(int sellerPort) {
        sellerPort = sellerPort;
    }

    public int getSellerPort() {
        return sellerPort;
    }

    public ManagerNetworkController() throws UnknownHostException {
        managerIp = NetworkUtil.getMyIP();
    }

    public void sendBidToSeller(Bid currentBid) throws IOException {
        ObjectSender pack = new ObjectSender(getManagerIp(), getManagerPort(), currentBid, Bid.class);
        NetworkUtil.send(getSellerAddress(), getSellerPort(), pack);
    }

    public void waitAskInitPackByBidder(BidStarter currentBidStarter) throws IOException, ClassNotFoundException, InterruptedException {
        Bid currentBid = currentBidStarter.getCurrentBid();
        while (!currentBid.isOver()) {
            try{
                ObjectSender request = NetworkUtil.receive(getManagerPort());
                if ((request.getObject()).equals("getBidderInitPackage")) {
                    sleep(1);
                    sendBidAndKey(currentBidStarter,request.getPORT_sender(), request.getIP_sender());
                }
            }catch (SocketTimeoutException ignored){}
        }
    }

    public void sendBidAndKey(BidStarter currentStarter, int portSender, String ipSender) throws IOException {
        ObjectSender pack = new ObjectSender(getManagerIp(), getManagerPort(), currentStarter, BidStarter.class);
        NetworkUtil.send(ipSender, portSender, pack);
    }

    public EncryptedPrices fetchEncryptedPrice() throws IOException, ClassNotFoundException {
        EncryptedPrices pack = null;
        try{
            ObjectSender request = NetworkUtil.receive(getManagerPort());
            pack = (EncryptedPrices) request.getObjectClass().cast(request.getObject());
        }
        catch (SocketTimeoutException ignored){}
        return pack;
    }

    public void sendWinnerAndPrice(Winner result) throws IOException {
        ObjectSender pack = new ObjectSender(getManagerIp(), getManagerPort(), result, Winner.class);
        NetworkUtil.send(getSellerAddress(), getSellerPort(), pack);
    }

}