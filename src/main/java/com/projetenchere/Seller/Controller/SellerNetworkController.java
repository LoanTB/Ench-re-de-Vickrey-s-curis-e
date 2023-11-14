package com.projetenchere.Seller.Controller;

import com.projetenchere.common.Models.Bid;
import com.projetenchere.common.Models.Encrypted.EncryptedOffer;
import com.projetenchere.common.Models.Encrypted.EncryptedPrices;
import com.projetenchere.common.Models.Network.NetworkContactInformation;
import com.projetenchere.common.Models.Network.Sendable.ObjectSender;
import com.projetenchere.common.Models.Network.Communication.Winner;
import com.projetenchere.common.Utils.Network.NetworkUtil;

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
            request = NetworkUtil.receive(sellerPort, 5000);
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
        try {
            return NetworkUtil.receive(sellerPort, 0);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendData(String IP, Integer PORT, Object data) throws IOException {
        ObjectSender objectSender = new ObjectSender(sellerIp, sellerPort,data,data.getClass());
        NetworkUtil.send(IP,PORT,objectSender);
    }
}