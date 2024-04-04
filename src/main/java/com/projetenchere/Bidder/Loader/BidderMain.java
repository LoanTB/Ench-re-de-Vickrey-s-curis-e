package com.projetenchere.bidder.loader;

import com.projetenchere.bidder.controller.BidderController;
import com.projetenchere.bidder.view.IBidderUserInterface;

import java.security.SignatureException;

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
        try {
            bidderController.initWithManager();
        } catch (SignatureException e) {
            throw new RuntimeException(e);
        }
        try {
            bidderController.readAndSendOffer();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}