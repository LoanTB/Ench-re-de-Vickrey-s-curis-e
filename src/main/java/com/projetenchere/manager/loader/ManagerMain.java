package com.projetenchere.manager.loader;

import com.projetenchere.manager.controller.ManagerController;
import com.projetenchere.manager.view.IManagerUserInterface;

import java.security.SignatureException;

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
        try {
            managerController.init();
        } catch (SignatureException e) {
            throw new RuntimeException(e);
        }
    }
}
