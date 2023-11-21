package com.projetenchere.Manager.Handlers;

import com.projetenchere.common.Controllers.NetworkController;
import com.projetenchere.common.Handlers.RequestHandler;
import com.projetenchere.common.Models.Network.Communication.Informations.PublicSecurityInformations;
import com.projetenchere.common.Models.Network.Communication.ObjectReceived;

public class BiddersInfosRequestHandler implements RequestHandler {
    private final NetworkController networkController;

    public BiddersInfosRequestHandler(NetworkController networkController) {
        this.networkController = networkController;
    }

    @Override
    public void handle(ObjectReceived objectReceived) throws Exception {
        for (PublicSecurityInformations publicSecurityInformations:networkController.getAnyInformationsOfType("Bidder")){
            networkController.sendTo(objectReceived.getAuthenticationStatus().authorOfSignature().getId(),publicSecurityInformations);
            wait(100);
        }
    }
}
