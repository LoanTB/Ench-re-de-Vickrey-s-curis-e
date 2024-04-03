package com.projetenchere.Manager.Loader;

import com.projetenchere.Manager.Controllers.ManagerController;
import com.projetenchere.Manager.View.IManagerUserInterface;
import com.projetenchere.Manager.View.commandLineInterface.ManagerCommandLineInterface;

public class ManagerCommandLineApp {

    public static void launchApp() {
        ManagerController controllerInstance = new ManagerController((IManagerUserInterface) new ManagerCommandLineInterface());
        controllerInstance.displayHello();
        try {
            controllerInstance.setSignatureConfig();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        controllerInstance.init();
    }

}







