package com.projetenchere.Manager.Loader;

import com.projetenchere.Bidder.BidderApp;
import com.projetenchere.Bidder.Controllers.BidderController;
import com.projetenchere.Manager.Controllers.ManagerController;
import com.projetenchere.Manager.ManagerApp;

public class ManagerMain extends Thread {
    public void run() {
        ManagerController controllerInstance = new ManagerController(ManagerApp.getViewInstance());
        controllerInstance.displayHello();
        try {
            controllerInstance.setSignatureConfig();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        controllerInstance.init();
    }
}
