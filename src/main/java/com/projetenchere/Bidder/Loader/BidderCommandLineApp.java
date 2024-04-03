package com.projetenchere.Bidder.Loader;

import com.projetenchere.Bidder.Controllers.BidderController;
import com.projetenchere.Bidder.View.IBidderUserInterface;
import com.projetenchere.Bidder.View.commandLineInterface.BidderCommandLineInterface;

public class BidderCommandLineApp {

    public static void launchApp() {
        BidderController controllerInstance = new BidderController((IBidderUserInterface) new BidderCommandLineInterface());
        controllerInstance.displayHello();
        try {
            controllerInstance.setSignatureConfig();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        controllerInstance.initWithManager();
        try {
            controllerInstance.readAndSendOffer();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}