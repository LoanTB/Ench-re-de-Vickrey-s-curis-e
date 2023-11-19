package com.projetenchere.Manager;

import com.projetenchere.Manager.Controller.ManagerController;

public class ManagerApp {
    public static void main(String[] args) throws Exception {
        ManagerController controller = new ManagerController();
        controller.displayHello();
        controller.initConnexion();
        controller.initBid();
//        System.out.println("Génération de clé...");
        controller.displayGenerateKey(); // "Génération de clé..."
        controller.generateManagerKeys();
//        System.out.println("Lancement de l'enchère ...");
        controller.displayBidLaunch(); // "Lancement de l'enchère..."
        controller.launchBids();

//        System.out.println("Reception des prix ..."); TODO : A incorporé dans les view
//        System.out.println("Traitement des prix...");
//        System.out.println("Envoie du prix gagnant...");
//        System.out.println("Fin des enchères !");
    }
}