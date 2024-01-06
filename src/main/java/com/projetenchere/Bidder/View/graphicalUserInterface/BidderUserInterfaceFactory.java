package com.projetenchere.Bidder.View.graphicalUserInterface;

import com.projetenchere.Bidder.View.IBidderUserInterface;

public class BidderUserInterfaceFactory implements IBidderUserInterfaceFactory {

    @Override
    public IBidderUserInterface createBidderUserInterface() {
        return new BidderGraphicalUserInterface();
    }
}

