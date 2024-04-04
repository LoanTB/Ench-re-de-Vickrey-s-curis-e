package com.projetenchere.bidder.loader;

import com.projetenchere.bidder.view.commandLineInterface.BidderCommandLineInterface;

public class BidderCommandLineApp {
    public static void launchApp() throws InterruptedException {
        BidderMain.setViewInstance(new BidderCommandLineInterface());
        BidderMain main = new BidderMain();
        main.start();
        main.join();
    }
}