package com.projetenchere.Bidder;

import com.projetenchere.Bidder.Controller.BidderController;

public class BidderApp {
    public static void main(String[] args) throws Exception {
        BidderController controller = new BidderController();
        controller.readName();
        controller.readPort();
        controller.fetchInitPackage();
        controller.showBid();
        controller.readAndSendOffer();
        controller.waitForPrice();
    }


}

