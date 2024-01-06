package com.projetenchere.Seller;

import com.projetenchere.Seller.Controllers.SellerController;
import com.projetenchere.Seller.View.graphicalUserInterface.ISellerUserInterfaceFactory;
import com.projetenchere.Seller.View.graphicalUserInterface.SellerUserInterfaceFactory;

public class SellerApp {
    public static void main(String[] args) throws Exception {
        ISellerUserInterfaceFactory uiFactory = new SellerUserInterfaceFactory();
        SellerController controller = new SellerController(uiFactory);
        controller.displayHello();
        controller.setSignatureConfig();
        controller.createMyBid();
        controller.sendMyBid();
        controller.receiveOffersUntilBidEndAndSendResults();
        controller.sendEncryptedOffersSet();
        controller.displayWinner();
    }
}

