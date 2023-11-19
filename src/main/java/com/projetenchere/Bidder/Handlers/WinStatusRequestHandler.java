package com.projetenchere.Bidder.Handlers;

import com.projetenchere.Bidder.Controllers.BidderController;
import com.projetenchere.common.Models.Network.Communication.WinStatus;
import com.projetenchere.common.Handlers.RequestHandler;
import com.projetenchere.common.Models.Network.Sendable.ObjectSender;

public class WinStatusRequestHandler implements RequestHandler {
    private final BidderController controller;

    public WinStatusRequestHandler(BidderController controller) {
        this.controller = controller;
    }

    @Override
    public void handle(ObjectSender objectSender) {
        controller.addResult((WinStatus) objectSender.getObject());
    }
}
