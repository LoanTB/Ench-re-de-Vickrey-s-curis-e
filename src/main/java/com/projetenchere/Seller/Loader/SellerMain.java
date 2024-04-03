package com.projetenchere.Seller.Loader;

import com.projetenchere.Seller.Controllers.SellerController;
import com.projetenchere.Seller.SellerApp;

public class SellerMain extends Thread {
    public void run() {
        SellerController sellerController = new SellerController(SellerApp.getViewInstance());
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
