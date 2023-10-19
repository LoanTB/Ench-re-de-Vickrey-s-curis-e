package com.projetenchere.Bidder;

import com.projetenchere.Bidder.Controller.BidderController;
import com.projetenchere.common.Model.Bid;
import com.projetenchere.common.Model.Offer;

public class BidderApp {
    public static void main(String[] args) {
        BidderController controller = new BidderController();
        Bid currentBid = controller.fetchInitPackage();
        controller.showBid(currentBid);
        if (controller.askSellerIfAlreadySentOffer()) {
            controller.whenAlreadySentOffer();
        } else {
            Offer offer = controller.readOfferFromInterface();
            controller.sendOffer(offer);
        }
    }


}

