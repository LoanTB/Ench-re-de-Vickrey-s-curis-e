package com.projetenchere.Seller;

import com.projetenchere.Seller.Controllers.SellerController;

public class SellerApp {
    public static void main(String[] args) throws Exception {
        SellerController controller = new SellerController();
        controller.displayHello();
        controller.setSignatureConfig();
        controller.createMyBid();
        controller.sendMyBid();
        controller.receiveOkUntilCheckEndAndSendResults();
        controller.sendEncryptedOffersSet();
        controller.displayWinner();
    }
}

