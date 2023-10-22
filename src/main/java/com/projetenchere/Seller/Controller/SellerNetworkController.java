package com.projetenchere.Seller.Controller;

import com.projetenchere.common.Model.Bid;
import com.projetenchere.common.Model.Encrypted.EncryptedOffer;
import com.projetenchere.common.Model.Encrypted.EncryptedPrices;
import com.projetenchere.common.Model.Serializable.ObjectSender;
import com.projetenchere.common.Model.Winner;
import com.projetenchere.common.Util.NetworkUtil;

import java.io.IOException;
import java.net.UnknownHostException;

public class SellerNetworkController {
    private final String SellerIp;
    private final int SellerPort = 24682;

    public SellerNetworkController() throws UnknownHostException {
        SellerIp = NetworkUtil.getMyIP();
    }

    public void sendEncryptedPrices(EncryptedPrices encryptedPrices) throws IOException {
        send(SellerIp,SellerPort,encryptedPrices);
    }

    public ObjectSender getEncryptedOfferRequests() throws IOException, ClassNotFoundException {
        ObjectSender request;
        do {
            request = NetworkUtil.receive(SellerPort);
        } while (!request.getObjectClass().equals(EncryptedOffer.class));
        return request;
    }

    public Bid getBidRequest() {
        ObjectSender request;
        do {
            request = waitRequest();
        } while (!request.getObjectClass().equals(Bid.class));
        return (Bid) request.getObjectClass().cast(request.getObject());
    }

    public Winner getWinnerRequest() {
        ObjectSender request;
        do {
            request = waitRequest();
        } while (!request.getObjectClass().equals(Winner.class));
        return (Winner) request.getObjectClass().cast(request.getObject());
    }

    public ObjectSender waitRequest(){
        while (true){
            try{
                return NetworkUtil.receive(SellerPort);
            } catch (IOException | ClassNotFoundException e){}
        }
    }

    public void send(String IP, Integer PORT, Object data) throws IOException {
        ObjectSender objectSender = new ObjectSender(SellerIp,SellerPort,data,data.getClass());
        NetworkUtil.send(IP,PORT,objectSender);
    }
}