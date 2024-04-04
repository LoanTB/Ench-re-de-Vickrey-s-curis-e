package com.projetenchere.Bidder.Loader;

import com.projetenchere.Bidder.Controllers.BidderController;
import com.projetenchere.Bidder.View.IBidderUserInterface;

public class BidderMain extends Thread {

    private static IBidderUserInterface viewInstance = null;
    public static IBidderUserInterface getViewInstance() {
        if (viewInstance == null) {throw new NullPointerException("Instance de vue non initialisée");}
        return viewInstance;
    }
    public static void setViewInstance(IBidderUserInterface instance) {
        viewInstance = instance;
    }

    public void run() {
        BidderController bidderController = new BidderController(viewInstance);
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
