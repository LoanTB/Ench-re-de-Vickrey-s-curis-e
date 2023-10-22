package com.projetenchere.Manager;

import com.projetenchere.Manager.Controller.ManagerController;

public class ManagerApp {
    public static void main(String[] args) throws Exception {
        System.out.println("Bienvenue Manager !");
        ManagerController controller = new ManagerController();

        controller.initBid();

        controller.generateManagerKeys();

        controller.launchBid();

        controller.waitEncryptedPrices();

        controller.processPrices();

        controller.endBid();

        System.out.println("Fin des enchères !");
    }
}