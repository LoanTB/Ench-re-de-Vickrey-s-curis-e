package com.projetenchere.Manager.Handlers;

import com.projetenchere.common.Controllers.NetworkController;
import com.projetenchere.common.Models.Network.Communication.CurrentBids;
import com.projetenchere.common.Models.Network.Communication.Informations.NetworkContactInformation;
import com.projetenchere.common.Handlers.RequestHandler;
import com.projetenchere.common.Models.Network.Communication.ObjectReceived;
import com.projetenchere.common.Models.Network.Sendable.ObjectSender;
import com.projetenchere.common.Utils.NetworkUtil;

import java.io.IOException;

public class InitPackageRequestHandler implements RequestHandler {
    private final NetworkController networkController;
    private final CurrentBids currentCurrentBidsStarters;

    public InitPackageRequestHandler(NetworkController networkController, CurrentBids currentCurrentBidsStarters) {
        this.currentCurrentBidsStarters = currentCurrentBidsStarters;
        this.networkController = networkController;
    }

    @Override
    public void handle(ObjectReceived objectReceived) throws Exception {
        networkController.sendTo(objectReceived.getAuthenticationStatus().authorOfSignature().getId(),currentCurrentBidsStarters);
    }

}
