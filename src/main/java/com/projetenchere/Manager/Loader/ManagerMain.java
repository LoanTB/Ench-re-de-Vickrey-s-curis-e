package com.projetenchere.Manager.Loader;

import com.projetenchere.Manager.Controllers.ManagerController;
import com.projetenchere.Manager.ManagerApp;

public class ManagerMain extends Thread {
    public void run() {
        ManagerController managerController = new ManagerController(ManagerApp.getViewInstance());
        ManagerGraphicalApp.setControllerInstance(managerController);
        managerController.displayHello();
        try {
            managerController.setSignatureConfig();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        managerController.init();
    }
}
