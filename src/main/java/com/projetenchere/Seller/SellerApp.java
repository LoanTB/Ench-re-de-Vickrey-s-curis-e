package com.projetenchere.Seller;

import com.projetenchere.Seller.Controllers.SellerController;

public class SellerApp {
    public static void main(String[] args) throws Exception {
        SellerController controller = new SellerController();
        controller.networkListeningInitialization();
        controller.initContactWithManager();
        controller.displayHello();
        controller.createMyBid();
        controller.receiveOffersUntilBidEnd();
        controller.sendEncryptedPrices();
        controller.displayEncryptedPriceSended();
        controller.waitFetchWinner();
        controller.displayWinner();
        controller.sendResults();
        controller.displayResultsSent();
    }
}

