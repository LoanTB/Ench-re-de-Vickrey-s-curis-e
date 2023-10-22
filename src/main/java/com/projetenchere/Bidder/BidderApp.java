package com.projetenchere.Bidder;

import com.projetenchere.Bidder.Controller.BidderController;

import java.io.IOException;

public class BidderApp {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        BidderController controller = new BidderController();
        controller.fetchInitPackage();
        controller.showBid();
        controller.readAndSendOffer();
        controller.waitForPrice();
    }


}

