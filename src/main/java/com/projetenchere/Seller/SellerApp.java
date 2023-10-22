package com.projetenchere.Seller;

import com.projetenchere.Seller.Controller.SellerController;

import java.io.IOException;
import java.util.List;

public class SellerApp {
    public static void main(String[] args) throws ClassNotFoundException, IOException {
        SellerController controller = new SellerController();

        controller.diplayHello();

        controller.requestCurrentBid();

        controller.receiveOffersUntilAuctionEnd();

        controller.sendEncryptedPrices();

        controller.displayEncryptedPriceSended();

        controller.requestWinner();

        controller.displayWinner();

        controller.sendResults();

        controller.displayResultsSent();
    }
}

