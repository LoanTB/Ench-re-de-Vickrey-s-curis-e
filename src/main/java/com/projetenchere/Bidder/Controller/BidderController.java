package com.projetenchere.Bidder.Controller;

import com.projetenchere.Bidder.View.IBidderUserInterface;
import com.projetenchere.Bidder.View.commandLineInterface.BidderCommandLineInterface;
import com.projetenchere.common.Model.Offer;

public class BidderController {
    public static final IBidderUserInterface ui = new BidderCommandLineInterface();

    public BidderController() {
    }

    public Offer readOfferFromInterface() {
        return ui.readOffer();
    }
}
