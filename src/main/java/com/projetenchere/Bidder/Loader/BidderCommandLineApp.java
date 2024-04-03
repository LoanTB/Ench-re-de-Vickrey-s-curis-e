package com.projetenchere.Bidder.Loader;

import com.projetenchere.Bidder.BidderApp;
import com.projetenchere.Bidder.Controllers.BidderController;
import com.projetenchere.Bidder.View.IBidderUserInterface;
import com.projetenchere.Bidder.View.commandLineInterface.BidderCommandLineInterface;
import com.projetenchere.Manager.ManagerApp;

public class BidderCommandLineApp {

    public static void launchApp() {
        BidderApp.setViewInstance(new BidderCommandLineInterface());
        (new BidderMain()).start();
    }
}