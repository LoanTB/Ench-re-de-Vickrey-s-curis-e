package com.projetenchere.Manager;

import com.projetenchere.Manager.Controllers.ManagerController;

public class ManagerApp {
    public static void main(String[] args) throws Exception {
        ManagerController controller = new ManagerController();
        controller.displayHello();
        controller.generateManagerKeys();
        controller.initConnexion();
        // TODO : Afficher avec ui "Gestionnaire prêt à traiter des enchères"
    }
}