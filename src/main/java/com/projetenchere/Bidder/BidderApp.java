package com.projetenchere.Bidder;

import com.projetenchere.Bidder.Controllers.BidderController;
import com.projetenchere.Bidder.View.graphicalUserInterface.IBidderUserInterfaceFactory;
import com.projetenchere.Bidder.View.graphicalUserInterface.BidderUserInterfaceFactory;

public class BidderApp {
    public static void main(String[] args) throws Exception {
        IBidderUserInterfaceFactory uiFactory = new BidderUserInterfaceFactory();
        BidderController controller = new BidderController(uiFactory);
        controller.displayHello();
        controller.setSignatureConfig();
        controller.initWithManager();
        controller.showBids();
        controller.readAndSendOffer();
    }
}
