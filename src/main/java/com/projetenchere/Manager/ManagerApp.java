package com.projetenchere.Manager;

import com.projetenchere.Manager.Controllers.ManagerController;

public class ManagerApp {
    public static void main(String[] args) throws Exception {
        ManagerController controller = new ManagerController();
        controller.displayHello();
        controller.init();
    }
}