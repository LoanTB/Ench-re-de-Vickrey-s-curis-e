package com.projetenchere.Seller;

import com.projetenchere.Seller.Controller.SellerController;
import com.projetenchere.Seller.Controller.SellerNetworkController;
import com.projetenchere.Seller.Model.Seller;
import com.projetenchere.common.Model.Encrypted.EncryptedOffer;
import com.projetenchere.common.Model.Winner;
import com.projetenchere.common.network.NetworkUtil;
import com.projetenchere.common.network.ObjectSender;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class SellerApp {
    public static void main(String[] args) throws ClassNotFoundException, IOException {
        Seller seller = new Seller();
        SellerController controller = new SellerController();
        SellerNetworkController networkController = new SellerNetworkController();

        controller.diplayHello();

        int end = 1;// TODO : Connaitre la date de fin d'une enchère
        int now = 0;

        EncryptedOffer offerRecieved;
        while (end-now > 0){
            try{
                ObjectSender request = networkController.getEncryptedOfferRequests();
                offerRecieved = (EncryptedOffer) request.getObjectClass().cast(request.getObject());
                seller.addEncryptedOffer(offerRecieved);
                seller.addBidderIp(request.getIP_serder());
                seller.addBidderPort(request.getPORT_sender());
                controller.displayOfferReceived(offerRecieved);
            } catch (SocketTimeoutException e){}
        }

        networkController.sendEncryptedPrices(controller.getEncryptedPrices(seller.getEncryptedOffers()));
        controller.displayEncryptedPriceSended();

        Winner winner = null;
        while (winner == null){
            try{
                winner = networkController.getWinnerRequests();
            } catch (SocketTimeoutException e){}
        }

        List<Double> biddersWinStatus = controller.getBiddersWinStatus(seller.getEncryptedOffers(),winner);
        controller.displayWinner(seller.getEncryptedOffers(),biddersWinStatus,winner);

        for (int i=0;i<biddersWinStatus.size();i++){
            ObjectSender data = new ObjectSender(networkController.getSellerIp(), networkController.getSellerPort(), biddersWinStatus.get(i), Double.class);
            NetworkUtil.send(seller.getBiddersIps().get(i),seller.getBiddersPorts().get(i),data);
        }

        controller.displayResultsSent();
    }
}

