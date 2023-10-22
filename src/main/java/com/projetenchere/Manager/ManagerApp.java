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
        System.out.println("Génération de clé...");
        controller.generateManagerKeys();
        System.out.println("Lancement de l'enchère ...");
        controller.launchBid(currentBid);
        System.out.println("Reception des prix ...");
        EncryptedPrices pricesReceived = controller.waitEncryptedPrices();
        System.out.println("Traitement des prix ...");
        Winner winnerForCurrentBid = controller.processPrices(pricesReceived);
        System.out.println("Prix traités. Gagnant : ");
        controller.showWinnerPrice(winnerForCurrentBid);
        controller.endBid(winnerForCurrentBid);
        System.out.println("Fin des enchères !");
    }
}