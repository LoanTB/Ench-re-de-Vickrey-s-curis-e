package com.projetenchere.Manager;

import com.projetenchere.Manager.Controller.ManagerController;
import com.projetenchere.common.Model.Bid;
import com.projetenchere.common.Model.Encrypted.EncryptedPrices;
import com.projetenchere.common.Model.Winner;

public class ManagerApp {
    public static void main(String[] args) throws Exception {
        System.out.println("Bienvenue Manager !");
        ManagerController controller = new ManagerController();
        Bid currentBid = controller.initBid();
        controller.generateManagerKeys();
        controller.launchBid(currentBid);
        EncryptedPrices pricesReceived = controller.waitEncryptedPrices();
        Winner winnerForCurrentBid = controller.processPrices(pricesReceived);
        controller.showWinnerPrice(winnerForCurrentBid);
        controller.endBid(winnerForCurrentBid);
        System.out.println("Fin des enchères !");


    }
}