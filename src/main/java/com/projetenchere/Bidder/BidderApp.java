package com.projetenchere.Bidder;

import com.projetenchere.Bidder.Controllers.BidderController;

public class BidderApp {
    public static void main(String[] args) throws Exception {
        BidderController controller = new BidderController();
        controller.displayHello();
        controller.readInfos();
        controller.sendBidderInfosToManager();
        controller.sendRequestOffers();
        controller.showBid();
        controller.readAndSendOffer();
        controller.waitForPrice();
    }


}

