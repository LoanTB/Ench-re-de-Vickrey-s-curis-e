package com.projetenchere.Bidder;

import com.projetenchere.Bidder.Controllers.BidderController;
import com.projetenchere.common.Models.Bid;
import com.projetenchere.exception.BidAbortedException;

public class BidderApp {
    public static void main(String[] args) throws Exception {
        BidderController controller = new BidderController();
        controller.displayHello();
        controller.setSignatureConfig();
        controller.initWithManager();
        controller.showBids();
        try {
            controller.readAndSendOffer();
        } catch (BidAbortedException e) {
            //TODO: comportement quand la bid est annulée
        }
    }
}