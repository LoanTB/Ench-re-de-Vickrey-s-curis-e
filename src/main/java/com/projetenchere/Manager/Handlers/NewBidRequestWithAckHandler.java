package com.projetenchere.Manager.Handlers;

import com.projetenchere.Manager.Controllers.ManagerController;
import com.projetenchere.common.Controllers.NetworkController;
import com.projetenchere.common.Handlers.RequestHandler;
import com.projetenchere.common.Models.Bid;
import com.projetenchere.common.Models.Network.Communication.Acknowledgment;
import com.projetenchere.common.Models.Network.Communication.ObjectReceived;

public class NewBidRequestWithAckHandler implements RequestHandler {
    private final NetworkController networkController;
    private final ManagerController controller;

    public NewBidRequestWithAckHandler(NetworkController networkController, ManagerController controller) {
        this.networkController = networkController;
        this.controller = controller;
    }

    @Override
    public void handle(ObjectReceived objectReceived) throws Exception {
        Bid bid = (Bid) objectReceived.getObjectSended().getObject();
        controller.addBid(bid);
        controller.startBid(bid.getId());
        networkController.sendTo(objectReceived.getAuthenticationStatus().authorOfSignature().getId(),new Acknowledgment("OK"));
    }
}
