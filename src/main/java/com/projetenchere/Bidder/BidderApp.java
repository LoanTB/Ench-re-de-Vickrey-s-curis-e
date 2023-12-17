package com.projetenchere.Bidder;

import com.projetenchere.Bidder.Controllers.BidderController;

public class BidderApp {
    public static void main(String[] args) throws Exception {
        BidderController controller = new BidderController();
        controller.displayHello();
        controller.setSignatureConfig();
        controller.initWithManager();
        controller.showBids();
        controller.readAndSendOffer();
        //controller.waitForPrice();
    }
}