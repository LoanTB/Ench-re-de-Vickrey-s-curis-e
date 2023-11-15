package com.projetenchere.Seller;

import com.projetenchere.Seller.Controller.SellerController;

import java.io.IOException;

public class SellerApp {
    public static void main(String[] args) throws IOException, InterruptedException {
        SellerController controller = new SellerController();
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

