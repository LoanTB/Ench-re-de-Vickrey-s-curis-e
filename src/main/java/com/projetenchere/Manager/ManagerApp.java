package com.projetenchere.Manager;

import com.projetenchere.Manager.Controller.ManagerController;
import com.projetenchere.Manager.Controller.ManagerNetworkController;
import com.projetenchere.common.Model.Bid;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;


public class ManagerApp {
    public static void main(String[] args) throws Exception {
        System.out.println("Bienvenue Manager !");

        ManagerController controller = new ManagerController();
        ManagerNetworkController networkController = new ManagerNetworkController();

        //Lancer enchère.

        Bid currentBid = controller.createBid();
        KeyPair ManagerKeys = controller.generateManagerKeys();

        PrivateKey managerPrivateKey = ManagerKeys.getPrivate();
        PublicKey managerPublicKey = ManagerKeys.getPublic();

        //TODO : Recevoir requête Enchérisseur.








    //Recevoir les prix.
        //ManagerNetworkController.fetchEncryptedPrice();
    //Traiter les prix.




    }
}

