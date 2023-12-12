package com.projetenchere.Bidder.network.Handlers;

import com.projetenchere.Bidder.Controllers.BidderController;
import com.projetenchere.common.Handlers.RequestHandler;
import com.projetenchere.common.Models.Network.Communication.CurrentBids;
import com.projetenchere.common.Models.Network.Communication.ObjectReceived;

public class CurrentBidsHandler implements RequestHandler {
    private final BidderController controller;

    public CurrentBidsHandler(BidderController controller) {
        this.controller = controller;
    }

    @Override
    public void handle(ObjectReceived objectReceived) {
        this.controller.setCurrentBids((CurrentBids) objectReceived.getObjectSended().getObject());
    }
}
