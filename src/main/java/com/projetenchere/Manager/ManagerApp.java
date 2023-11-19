package com.projetenchere.Manager;

import com.projetenchere.Manager.Controller.ManagerController;

public class ManagerApp {
    public static void main(String[] args) throws Exception {
        ManagerController controller = new ManagerController();
        controller.diplayHello();
        controller.initConnexion();


        //Recevoir l'enchère créé de seller.
        //TODO : Recevoir l'encère créé de seller
        //TODO : "Supprimer" les méthoides de création par manager.

            // Enregistrer les informations de seller.
            //Envoyer mes informations au seller.
        controller.initContactWithSeller();


        controller.initBid();
        System.out.println("Génération de clé...");
        controller.generateManagerKeys();
        System.out.println("Lancement de l'enchère ...");
        controller.launchBids();
//        System.out.println("Reception des prix ..."); TODO : A incorporé dans les view
//        System.out.println("Traitement des prix...");
//        System.out.println("Envoie du prix gagnant...");
//        System.out.println("Fin des enchères !");
    }
}