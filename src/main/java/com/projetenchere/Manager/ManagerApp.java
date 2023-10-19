package com.projetenchere.Manager;

import com.projetenchere.Manager.Controller.ManagerController;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class ManagerApp {
    public static void main(String[] args) throws Exception {
        System.out.println("Bienvenue Manager !");

        ManagerController controller = new ManagerController();
        //Lancer enchère.

        KeyPair ManagerKeys = controller.generateManagerKeys();


        PrivateKey managerPrivateKey = ManagerKeys.getPrivate();
        PublicKey managerPublicKey = ManagerKeys.getPublic();

        //Recevoir les prix.

        //Traiter les prix.

        //Manager.priceProcessing(BidderPrice);



    }
}

