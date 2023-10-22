package com.projetenchere.Seller.Controller;

import com.projetenchere.common.Model.Bid;
import com.projetenchere.common.Model.Encrypted.EncryptedOffer;
import com.projetenchere.common.Model.Encrypted.EncryptedPrices;
import com.projetenchere.common.Model.Sendable.ObjectSender;
import com.projetenchere.common.Model.Winner;
import com.projetenchere.common.Util.NetworkUtil;

import java.io.IOException;
import java.net.UnknownHostException;

public class SellerNetworkController {
    private final String sellerIp;
    private final String managerIp;
    private final int sellerPort = 24682;
    private final int managerPort = 24683;

    public SellerNetworkController() throws UnknownHostException {
        sellerIp = NetworkUtil.getMyIP();
        managerIp = NetworkUtil.getMyIP();
    }

    public void sendEncryptedPrices(EncryptedPrices encryptedPrices) throws IOException {
        sendData(managerIp, managerPort,encryptedPrices);
    }

    public ObjectSender fetchEncryptedOffer() throws IOException, ClassNotFoundException {
        ObjectSender request;
        do {
            request = NetworkUtil.receive(sellerPort);
        } while (!request.getObjectClass().equals(EncryptedOffer.class));
        return request;
    }

    public Bid fetchBid() {
        ObjectSender request;
        do {
            request = waitData();
        } while (!request.getObjectClass().equals(Bid.class));
        return (Bid) request.getObjectClass().cast(request.getObject());
    }

    public Winner fetchWinner() {
        ObjectSender request;
        do {
            request = waitData();
        } while (!request.getObjectClass().equals(Winner.class));
        return (Winner) request.getObjectClass().cast(request.getObject());
    }

    public ObjectSender waitData(){
        while (true){
            try{
                return NetworkUtil.receive(sellerPort);
            } catch (IOException | ClassNotFoundException ignored){}
        }
    }

    public void sendData(String IP, Integer PORT, Object data) throws IOException {
        ObjectSender objectSender = new ObjectSender(sellerIp, sellerPort,data,data.getClass());
        NetworkUtil.send(IP,PORT,objectSender);
    }
}