package com.projetenchere.common.Models.Network.Handlers;

import com.projetenchere.common.Models.Network.Communication.SecurityInformations;
import com.projetenchere.common.Models.Network.RequestHandler;
import com.projetenchere.common.Models.Network.Sendable.ObjectSender;
import com.projetenchere.common.Models.Network.NetworkController;

public class InformationsRequestHandler implements RequestHandler {
    private final NetworkController networkController;

    public InformationsRequestHandler(NetworkController networkController) {
        this.networkController = networkController;
    }

    @Override
    public void handle(ObjectSender objectSender) {
        networkController.saveInformations((SecurityInformations) objectSender.getObject());
    }
}
