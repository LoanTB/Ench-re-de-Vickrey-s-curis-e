package com.projetenchere.Seller.Loader;

import com.projetenchere.Seller.Controllers.SellerController;
import com.projetenchere.Seller.View.ISellerUserInterface;
import com.projetenchere.Seller.View.commandLineInterface.SellerCommandLineInterface;
import javafx.application.Platform;

public class SellerMain extends Thread {
    public void run() {
        SellerController sellerController = new SellerController((ISellerUserInterface) new SellerCommandLineInterface());
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
