package com.projetenchere.Seller;

import com.projetenchere.Seller.Controller.SellerController;
import com.projetenchere.Seller.Model.Seller;
import com.projetenchere.common.Model.Bid;
import com.projetenchere.common.Model.Encrypted.EncryptedOffer;
import com.projetenchere.common.Model.Serializable.ObjectSender;
import com.projetenchere.common.Model.Winner;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;

public class SellerApp {
    public static void main(String[] args) throws ClassNotFoundException, IOException {
        Seller seller = new Seller();
        SellerController controller = new SellerController();

        controller.diplayHello();

        Bid actualBid = controller.getBid();

        EncryptedOffer offerReceived;
        while (!actualBid.isOver()){
            try{
                ObjectSender request = controller.getEncryptedOfferRequests();
                offerReceived = (EncryptedOffer) request.getObjectClass().cast(request.getObject());
                seller.addEncryptedOffer(offerReceived);
                seller.addBidderIp(request.getIP_sender());
                seller.addBidderPort(request.getPORT_sender());
                controller.displayOfferReceived(offerReceived);
            } catch (SocketTimeoutException e){}
        }

        controller.sendEncryptedPrices(seller.getEncryptedOffers());
        controller.displayEncryptedPriceSended();

        Winner winner = controller.getWinner();

        List<Double> biddersWinStatus = controller.getBiddersWinStatus(seller.getEncryptedOffers(),winner);
        controller.displayWinner(seller.getEncryptedOffers(),biddersWinStatus,winner);

        controller.sendResults(seller.getBiddersIps(),seller.getBiddersPorts(),biddersWinStatus);

        controller.displayResultsSent();
    }
}

