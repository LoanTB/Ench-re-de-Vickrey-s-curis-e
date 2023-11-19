package com.projetenchere.Bidder.Handlers;

import com.projetenchere.Bidder.Controllers.BidderController;
import com.projetenchere.Bidder.Controllers.BidderNetworkController;
import com.projetenchere.common.Handlers.RequestHandler;
import com.projetenchere.common.Models.Network.Communication.Informations.PublicSecurityInformations;
import com.projetenchere.common.Models.Network.Communication.ObjectReceived;
import com.projetenchere.common.Models.Network.Sendable.ObjectSender;

public class PublicSecurityInformationHandler implements RequestHandler {
    public final BidderNetworkController controller;

    public PublicSecurityInformationHandler(BidderNetworkController controller) {
        this.controller = controller;
    }

    @Override
    public void handle(ObjectReceived objectReceived) {
        this.controller.saveInformations((PublicSecurityInformations) objectReceived.getObjectSended().getObject());
    }
}
