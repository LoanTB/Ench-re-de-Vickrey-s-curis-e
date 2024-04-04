package com.projetenchere.Seller.Loader;

import com.projetenchere.Seller.Controllers.SellerController;
import com.projetenchere.Seller.View.ISellerUserInterface;

public class SellerMain extends Thread {

    private static ISellerUserInterface viewInstance = null;
    public static ISellerUserInterface getViewInstance() {
        if (viewInstance == null) {throw new NullPointerException("Instance de vue non initialisée");}
        return viewInstance;
    }
    public static void setViewInstance(ISellerUserInterface instance) {
        viewInstance = instance;
    }

    public void run() {
        SellerController sellerController = new SellerController(viewInstance);
        SellerGraphicalApp.setControllerInstance(sellerController);
        sellerController.displayHello();
        try {
            sellerController.setSignatureConfig();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        sellerController.createMyBid();
        sellerController.sendMyBid();
        sellerController.receiveOkUntilCheckEndAndSendResults();
        try {
            sellerController.sendEncryptedOffersSet();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        sellerController.displayWinner();
    }
}
