package com.projetenchere.Manager.Loader;

import com.projetenchere.Bidder.BidderApp;
import com.projetenchere.Bidder.View.commandLineInterface.BidderCommandLineInterface;
import com.projetenchere.Manager.Controllers.ManagerController;
import com.projetenchere.Manager.ManagerApp;
import com.projetenchere.Manager.View.IManagerUserInterface;
import com.projetenchere.Manager.View.commandLineInterface.ManagerCommandLineInterface;

public class ManagerCommandLineApp {
    public static void launchApp() {
        ManagerApp.setViewInstance(new ManagerCommandLineInterface());
        (new ManagerMain()).start();
    }

}







