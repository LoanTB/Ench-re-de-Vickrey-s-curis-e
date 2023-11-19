package com.projetenchere.Bidder.Handlers;

import com.projetenchere.Bidder.Controllers.BidderController;
import com.projetenchere.common.Handlers.RequestHandler;
import com.projetenchere.common.Models.Network.Communication.CurrentBidsPublicKeys;
import com.projetenchere.common.Models.Network.Sendable.ObjectSender;

public class CurrentBidsPublicKeysHandler implements RequestHandler {
    private final BidderController controller;

    public CurrentBidsPublicKeysHandler(BidderController controller) {
        this.controller = controller;
    }

    @Override
    public void handle(ObjectSender objectSender) {
        this.controller.setCurrentBidsPublicKeys((CurrentBidsPublicKeys) objectSender.getObject());
    }
}
