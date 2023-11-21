package com.projetenchere.Manager.Handlers;

import com.projetenchere.common.Controllers.NetworkController;
import com.projetenchere.common.Models.Network.Communication.CurrentBids;
import com.projetenchere.common.Models.Network.Communication.CurrentBidsPublicKeys;
import com.projetenchere.common.Handlers.RequestHandler;
import com.projetenchere.common.Models.Network.Communication.Informations.PublicSecurityInformations;
import com.projetenchere.common.Models.Network.Communication.ObjectReceived;

public class InitPackageRequestHandler implements RequestHandler {
    private final NetworkController networkController;
    private final CurrentBids currentBids;
    private final CurrentBidsPublicKeys currentBidsPublicKeys;

    public InitPackageRequestHandler(NetworkController networkController, CurrentBids currentBids, CurrentBidsPublicKeys currentBidsPublicKeys) {
        this.currentBids = currentBids;
        this.networkController = networkController;
        this.currentBidsPublicKeys = currentBidsPublicKeys;
    }

    @Override
    public void handle(ObjectReceived objectReceived) throws Exception {
        synchronized (this) {
            for (PublicSecurityInformations publicSecurityInformations:networkController.getAnyInformationsOfType("Seller")){
                networkController.sendTo(objectReceived.getAuthenticationStatus().authorOfSignature().getId(),publicSecurityInformations);
                wait(100);
            }
            wait(100);
            networkController.sendTo(objectReceived.getAuthenticationStatus().authorOfSignature().getId(),currentBids);
            wait(100);
            networkController.sendTo(objectReceived.getAuthenticationStatus().authorOfSignature().getId(),currentBidsPublicKeys);
        }
    }

}
