package com.projetenchere.Seller;

import com.projetenchere.Seller.Controller.SellerController;

public class SellerApp {
    public static void main(String[] args) throws Exception {
        SellerController controller = new SellerController();
        controller.initConnexion();
        controller.initContactWithManager();
        controller.diplayHello();
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

