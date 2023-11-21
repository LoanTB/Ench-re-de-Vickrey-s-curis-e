package com.projetenchere.Manager.Handlers;

import com.projetenchere.common.Controllers.NetworkController;
import com.projetenchere.common.Models.Network.Communication.CurrentBids;
import com.projetenchere.common.Models.Network.Communication.CurrentBidsPublicKeys;
import com.projetenchere.common.Handlers.RequestHandler;
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
        System.out.println("A");
        networkController.sendTo(objectReceived.getAuthenticationStatus().authorOfSignature().getId(),currentBidsPublicKeys); // TODO : Bloque
        System.out.println("B");
        wait(100);
        System.out.println("C");
        networkController.sendTo(objectReceived.getAuthenticationStatus().authorOfSignature().getId(),currentBids);
        System.out.println("D");
    }

}
