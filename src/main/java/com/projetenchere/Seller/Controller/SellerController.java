package com.projetenchere.Seller.Controller;

import com.projetenchere.Seller.View.ISellerUserInterface;
import com.projetenchere.Seller.View.commandLineInterface.SellerCommandLineInterface;
import com.projetenchere.common.Model.Encrypted.EncryptedOffer;
import com.projetenchere.common.Model.Encrypted.EncryptedPrices;
import com.projetenchere.common.Model.Winner;

import java.util.*;


public class SellerController {

    public static final ISellerUserInterface ui = new SellerCommandLineInterface();


    public void diplayHello(){
        ui.diplayHello();
    }

    public void displayOfferReceived(EncryptedOffer encryptedOffer){
        ui.displayOfferReceived(encryptedOffer);
    }

    public void displayWinner(List<EncryptedOffer> encryptedOffers, List<Double> biddersWinStatus, Winner winner){
        String winnerID = this.getWinnerID(encryptedOffers,biddersWinStatus);
        Double price = winner.getPriceToPay();
        ui.displayWinner(winnerID,price);
    }

    public void displayEncryptedPriceSended(){
        ui.displayEncryptedPriceSended();
    }

    public void displayResultsSent(){
        ui.displayResultsSent();
    }

    public EncryptedPrices getEncryptedPrices(List<EncryptedOffer> encryptedOffers){
        Set<byte[]> encryptedPrices = new HashSet<>();
        for (EncryptedOffer encryptedOffer: encryptedOffers){
            encryptedPrices.add(encryptedOffer.getPrice());
        }
        return new EncryptedPrices(encryptedPrices);
    }

    public List<Double> getBiddersWinStatus(List<EncryptedOffer> encryptedOffers, Winner winner){
        boolean haveAWinner = false;
        List<Double> winStatus = new ArrayList<Double>();
        for (int i=0;i<encryptedOffers.size();i++){
            if (Arrays.equals(encryptedOffers.get(i).getPrice(), winner.getEncryptedMaxprice()) && !haveAWinner){
                winStatus.add(winner.getPriceToPay());
                haveAWinner = true;
            } else {
                winStatus.add(-1.0);
            }
        }
        return winStatus;
    }

    public String getWinnerID(List<EncryptedOffer> encryptedOffers, List<Double> biddersWinStatus){
        for (int i=0;i<encryptedOffers.size();i++){
            if (biddersWinStatus.get(i) != -1.0){
                return encryptedOffers.get(i).getIdBidder();
            }
        }
        return null;
    }

}