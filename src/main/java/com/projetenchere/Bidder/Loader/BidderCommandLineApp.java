package com.projetenchere.Bidder.Loader;

import com.projetenchere.Bidder.View.commandLineInterface.BidderCommandLineInterface;

public class BidderCommandLineApp {
    public static void launchApp() throws InterruptedException {
        BidderMain.setViewInstance(new BidderCommandLineInterface());
        BidderMain main = new BidderMain();
        main.start();
        main.join();
    }
}