package com.projetenchere.Manager;

import com.projetenchere.Manager.Controller.ManagerController;

public class ManagerApp {
    public static void main(String[] args) throws Exception {
        ManagerController controller = new ManagerController();
        controller.diplayHello();
        //TODO : Generer mes clés.
        controller.generateManagerKeys();

        controller.initConnexion();

        //Recevoir l'enchère créé de seller.
        //TODO : Recevoir l'enchère créé de seller
        //TODO : "Supprimer" les méthodes de création par manager.

            // Enregistrer les informations de seller.
            //Envoyer mes informations au seller.
        controller.initContactWithSeller();

//        controller.initBid(); //TODO : Seller qui fait ça mtn ?

        controller.launchBids();

    }
}