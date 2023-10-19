package com.projetenchere.Bidder.Controller;

import com.projetenchere.Bidder.Controller.network.BidderNetworkController;
import com.projetenchere.Bidder.View.IBidderUserInterface;
import com.projetenchere.Bidder.View.commandLineInterface.BidderCommandLineInterface;
import com.projetenchere.common.Model.Bid;
import com.projetenchere.common.Model.BidStarter;
import com.projetenchere.common.Model.Offer;

import java.io.IOException;
import java.security.PublicKey;

public class BidderController {
    public final IBidderUserInterface ui = new BidderCommandLineInterface();
    public final BidderNetworkController network = new BidderNetworkController();
    public PublicKey publicKey;
    public Bid currentBid;


    public Offer readOfferFromInterface() {
        return ui.readOffer();
    }

    public void loadInitPackage() throws IOException, ClassNotFoundException {
         BidStarter bidStart = network.askForInitPackage();
         this.currentBid = bidStart.getCurrentBid();
         this.publicKey = bidStart.getManagerPublicKey();


    }

    public boolean askSellerIfAlreadySentOffer() {
        //TODO: ask seller over network
        return true;
    }

    public void showBid(Bid bid) {
        ui.displayBid(bid);
    }

    public void sendOffer(Offer offer) {
        //TODO: send offer with network
    }

    private int fetchPriceToPay() {
        // returns -1 if offer lost
        //TODO: ask over network
        return 0;
    }

    private void checkWinAndTell() {
         int priceToPay = fetchPriceToPay();
         if (priceToPay == -1) {
             ui.tellOfferLost();
         }
         else {
             ui.tellOfferWon(priceToPay);
         }

    }

    public void whenAlreadySentOffer() {
        if (fetchInitPackage().isOver()) {
            checkWinAndTell();
        } else {
            ui.tellOfferAlreadySent();
        }
    }


}
