package com.projetenchere.Bidder.Loader;

import com.projetenchere.Bidder.BidderApp;
import com.projetenchere.Bidder.Controllers.BidderController;

public class BidderMain extends Thread {
    public void run() {
        BidderController bidderController = new BidderController(BidderApp.getViewInstance());
        BidderGraphicalApp.setControllerInstance(bidderController);
        bidderController.displayHello();
        try {
            bidderController.setSignatureConfig();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        bidderController.initWithManager();
        try {
            bidderController.readAndSendOffer();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
