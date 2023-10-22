package com.projetenchere.Seller;

import com.projetenchere.Seller.Controller.SellerController;

import java.io.IOException;

public class SellerApp {
    public static void main(String[] args) throws ClassNotFoundException, IOException {
        SellerController controller = new SellerController();

        controller.diplayHello();

        controller.fetchCurrentBid();

        controller.receiveOffersUntilAuctionEnd();

        controller.sendEncryptedPrices();

        controller.displayEncryptedPriceSended();

        controller.fetchWinner();

        controller.displayWinner();

        controller.sendResults();

        controller.displayResultsSent();
    }
}

