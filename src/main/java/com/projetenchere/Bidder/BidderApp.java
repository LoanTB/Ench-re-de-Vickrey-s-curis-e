package com.projetenchere.Bidder;

import com.projetenchere.Bidder.Controller.BidderController;
import com.projetenchere.Bidder.Controller.BidderNetworkController;
import com.projetenchere.Bidder.Model.Bidder;
import com.projetenchere.common.Model.Bid;
import com.projetenchere.common.Model.Offer;

public class BidderApp {
    public static void main(String[] args) {
        BidderController controller = new BidderController();
        Bid currentBid = controller.fetchCurrentBid();
        controller.showBid(currentBid);
        if (controller.askSellerIfAlreadySentOffer()) {
            controller.whenAlreadySentOffer();
        } else {
            Offer offer = controller.readOfferFromInterface();
            controller.sendOffer(offer);
        }
    }


}

