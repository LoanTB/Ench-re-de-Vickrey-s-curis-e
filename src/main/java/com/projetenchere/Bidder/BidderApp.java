package com.projetenchere.Bidder;

import com.projetenchere.Bidder.Controller.BidderController;
import com.projetenchere.common.Model.Bid;
import com.projetenchere.common.Model.Offer;

import java.io.IOException;

public class BidderApp {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        BidderController controller = new BidderController();
        controller.fetchInitPackage();
        controller.showBid();
        if (controller.askSellerIfAlreadySentOffer()) {
            controller.waitForPrice();
        } else {
            Offer offer = controller.readOfferFromInterface();
            controller.sendOffer(offer);
        }
    }


}

