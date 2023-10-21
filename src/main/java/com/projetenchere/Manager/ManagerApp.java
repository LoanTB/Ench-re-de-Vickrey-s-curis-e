package com.projetenchere.Manager;

import com.projetenchere.Manager.Controller.ManagerController;
import com.projetenchere.Manager.Controller.ManagerNetworkController;
import com.projetenchere.Manager.Model.Manager;
import com.projetenchere.common.Model.Bid;
import com.projetenchere.common.Model.BidStarter;
import com.projetenchere.common.Model.Winner;
import com.projetenchere.common.Model.Encrypted.EncryptedPrices;

import java.util.Set;


public class ManagerApp {
    public static void main(String[] args) throws Exception {
        System.out.println("Bienvenue Manager !");
        Manager manager = new Manager();
        ManagerController controller = new ManagerController();
        ManagerNetworkController networkController = new ManagerNetworkController();

        Bid currentBid = controller.createBid();
        System.out.println("Vous avez créé l'enchère : ");
        System.out.println(currentBid._toString());

        manager.setManagerKeys(controller.generateManagerKeys());

        manager.setSellerAddress(controller.askSellerAddress());

        BidStarter currentBidStarter = new BidStarter(manager.getManagerPublicKey(),currentBid,manager.getSellerAddress());

        networkController.waitAskInitPackByBidder(currentBidStarter);

        EncryptedPrices currentEncryptedPrices = networkController.fetchEncryptedPrice();

        Set<Double> currentDecryptedPrices = controller.decryptEncryptedPrice(currentEncryptedPrices, manager.getManagerPrivateKey());

        controller.showPrices(currentDecryptedPrices);

        Winner winnerForCurrentBid = controller.getWinnerPrice(manager.getManagerPublicKey(), currentDecryptedPrices);

        networkController.sendWinnerAndPrice(manager.getSellerAddress(), winnerForCurrentBid);

        System.out.println("Fin des enchères !");
    }
}

