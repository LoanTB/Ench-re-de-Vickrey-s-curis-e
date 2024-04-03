package com.projetenchere.Bidder.Loader;

import com.projetenchere.Bidder.BidderApp;
import com.projetenchere.Bidder.View.commandLineInterface.BidderCommandLineInterface;

public class BidderCommandLineApp {

    public static void launchApp() {
        BidderApp.setViewInstance(new BidderCommandLineInterface());
        (new BidderMain()).start();
    }
}