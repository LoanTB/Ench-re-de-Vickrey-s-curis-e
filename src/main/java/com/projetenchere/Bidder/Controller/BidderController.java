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

    public void showBid(Bid bid) {
        ui.displayBid(bid);
    }

    public void sendOffer(Offer offer) {
        //TODO: send offer with network
    }
}
