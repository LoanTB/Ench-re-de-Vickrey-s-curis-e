package com.projetenchere.Manager;

import com.projetenchere.Manager.Controllers.ManagerController;
import com.projetenchere.Manager.View.IManagerUserInterfaceFactory;
import com.projetenchere.Manager.View.graphicalUserInterface.ManagerUserInterfaceFactory;

public class ManagerApp {
    public static void main(String[] args) throws Exception {
        IManagerUserInterfaceFactory uiFactory = new ManagerUserInterfaceFactory();
        ManagerController controller = new ManagerController(uiFactory);
        controller.displayHello();
        controller.setSignatureConfig();
        controller.init();
    }
}
