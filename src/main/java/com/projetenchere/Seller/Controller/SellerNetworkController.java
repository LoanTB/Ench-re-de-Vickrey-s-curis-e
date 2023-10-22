package com.projetenchere.Seller.Controller;

import com.projetenchere.common.Model.Encrypted.EncryptedOffer;
import com.projetenchere.common.Model.Encrypted.EncryptedPrices;
import com.projetenchere.common.Model.Winner;
import com.projetenchere.common.network.NetworkUtil;
import com.projetenchere.common.network.ObjectSender;

import java.io.IOException;
import java.net.UnknownHostException;

public class SellerNetworkController {
    private final String SellerIp;
    private final int SellerPort = 24682;

    public SellerNetworkController() throws UnknownHostException {
        SellerIp = NetworkUtil.getMyIP();
    }

    public void sendEncryptedPrices(EncryptedPrices encryptedPrices) throws IOException {
        ObjectSender objectSender = new ObjectSender(SellerIp,SellerPort,encryptedPrices,encryptedPrices.getClass());
        NetworkUtil.send(SellerIp,SellerPort,objectSender);
    }



    public ObjectSender getEncryptedOfferRequests() throws IOException, ClassNotFoundException {
        ObjectSender request;
        do {
            request = NetworkUtil.receive(SellerPort);
        } while (!request.getObjectClass().equals(EncryptedOffer.class));
        return request;
    }

    public Winner getWinnerRequests() throws IOException, ClassNotFoundException {
        ObjectSender request;
        do {
            request = NetworkUtil.receive(SellerPort);
        } while (!request.getObjectClass().equals(Winner.class));
        return (Winner) request.getObjectClass().cast(request.getObject());
    }

    public String getSellerIp() {
        return SellerIp;
    }

    public int getSellerPort() {
        return SellerPort;
    }
}