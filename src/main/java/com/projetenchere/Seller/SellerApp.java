package com.projetenchere.Seller;

import com.projetenchere.Seller.Controllers.SellerController;

public class SellerApp {
    public static void main(String[] args) throws Exception {
        SellerController controller = new SellerController();
        controller.displayHello();
        controller.readInfos();
        controller.sendSellerInfosToManager();
        controller.createMyBid();
        controller.receiveOffersUntilBidEnd();
        controller.sendEncryptedPrices();
        controller.waitFetchWinner();
        controller.displayWinner();
        controller.sendResults();
    }
}

