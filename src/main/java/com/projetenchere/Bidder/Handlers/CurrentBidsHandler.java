package com.projetenchere.Bidder.Handlers;

import com.projetenchere.Bidder.Controllers.BidderController;
import com.projetenchere.common.Handlers.RequestHandler;
import com.projetenchere.common.Models.Network.Communication.CurrentBids;
import com.projetenchere.common.Models.Network.Sendable.ObjectSender;

public class CurrentBidsHandler implements RequestHandler {
    private final BidderController controller;

    public CurrentBidsHandler(BidderController controller) {
        this.controller = controller;
    }

    @Override
    public void handle(ObjectSender objectSender) {
        this.controller.setCurrentBids((CurrentBids) objectSender.getObject());
    }
}
