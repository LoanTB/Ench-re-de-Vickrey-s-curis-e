package com.projetenchere.Manager.Loader;

import com.projetenchere.Manager.Controllers.ManagerController;
import com.projetenchere.Manager.View.IManagerUserInterface;

public class ManagerMain extends Thread {

    private static IManagerUserInterface viewInstance = null;
    public static IManagerUserInterface getViewInstance() {
        if (viewInstance == null) {throw new NullPointerException("Instance de vue non initialisée");}
        return viewInstance;
    }
    public static void setViewInstance(IManagerUserInterface instance) {
        viewInstance = instance;
    }

    public void run() {
        ManagerController managerController = new ManagerController(viewInstance);
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
