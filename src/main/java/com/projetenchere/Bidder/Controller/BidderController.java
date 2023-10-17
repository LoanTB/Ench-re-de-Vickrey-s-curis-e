package com.projetenchere.Bidder.Controller;

import com.projetenchere.Bidder.View.IBidderUserInterface;
import com.projetenchere.Bidder.View.commandLineInterface.BidderCommandLineInterface;
import com.projetenchere.common.Model.Bid;
import com.projetenchere.common.Model.Offer;

public class BidderController {
    public static final IBidderUserInterface ui = new BidderCommandLineInterface();


    public Offer readOfferFromInterface() {
        return ui.readOffer();
    }

    public Bid fetchCurrentBid() {
        return new Bid();
        //TODO: fetch Bid with network
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
        if (fetchCurrentBid().isOver()) {
            checkWinAndTell();
        } else {
            ui.tellOfferAlreadySent();
        }
    }


}
