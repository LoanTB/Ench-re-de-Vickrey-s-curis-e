package com.projetenchere.Manager;

import com.projetenchere.Manager.Controller.ManagerController;

public class ManagerApp {
    public static void main(String[] args) throws Exception {
        ManagerController controller = new ManagerController();
        controller.displayHello();
        controller.initConnexion();

        //Recevoir l'enchère créé de seller.
        //TODO : Recevoir l'enchère créé de seller
        //TODO : "Supprimer" les méthodes de création par manager.
        // Enregistrer les informations de seller.
        //Envoyer mes informations au seller.
        controller.initContactWithSeller();

        controller.initBid();
        controller.displayGenerateKey(); // "Génération de clé..."
        controller.generateManagerKeys();
        controller.displayBidLaunch(); // "Lancement de l'enchère..."
        controller.launchBids();
        controller.displayReceivedPrices(); // Réception des prix...
        controller.displayPriceProcessing(); // Traitement des prix...
        controller.displaySentWinnerPrice(); // Envoie du prix gagnant...
        controller.displayEndOfAuction(); // Fin des ehcères !
    }
}