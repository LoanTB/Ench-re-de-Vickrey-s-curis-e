package com.projetenchere.Bidder.network.Handlers;

import com.projetenchere.Bidder.Controllers.BidderController;
import com.projetenchere.common.Models.Network.Communication.ObjectReceived;
import com.projetenchere.common.Models.Network.Communication.WinStatus;
import com.projetenchere.common.Handlers.RequestHandler;

public class WinStatusRequestHandler implements RequestHandler {
    private final BidderController controller;

    public WinStatusRequestHandler(BidderController controller) {
        this.controller = controller;
    }

    @Override
    public void handle(ObjectReceived objectReceived) {
        WinStatus result = (WinStatus) objectReceived.getObjectSended().getObject();
        controller.addResult(result);
    }
}
