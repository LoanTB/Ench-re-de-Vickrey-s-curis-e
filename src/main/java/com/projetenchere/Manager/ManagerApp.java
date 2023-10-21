package com.projetenchere.Manager;

import com.projetenchere.Manager.Controller.ManagerController;
import com.projetenchere.Manager.Controller.ManagerNetworkController;
import com.projetenchere.common.Model.Bid;
import com.projetenchere.common.Model.BidStarter;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;


public class ManagerApp {
    public static void main(String[] args) throws Exception {
        System.out.println("Bienvenue Manager !");
        ManagerController controller = new ManagerController();
        ManagerNetworkController networkController = new ManagerNetworkController();

        Bid currentBid = controller.createBid();
        System.out.println("Vous avez créé l'enchère : ");
        System.out.println(currentBid._toString());


        KeyPair ManagerKeys = controller.generateManagerKeys();
        PrivateKey managerPrivateKey = ManagerKeys.getPrivate();
        PublicKey managerPublicKey = ManagerKeys.getPublic();


        String sellerAdress = controller.askSellerAdress();
        BidStarter currentBidStarter = new BidStarter(managerPublicKey,currentBid,sellerAdress);

        networkController.waitAskInitPackByBidder(currentBidStarter);

        //networkController.fetchEncryptedPrice();

    }
}

