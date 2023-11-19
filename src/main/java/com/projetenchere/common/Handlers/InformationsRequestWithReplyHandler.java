package com.projetenchere.common.Handlers;

import com.projetenchere.common.Models.Network.Communication.Informations.PublicSecurityInformations;
import com.projetenchere.common.Controllers.NetworkController;
import com.projetenchere.common.Models.Network.Sendable.ObjectSender;
import com.projetenchere.common.Utils.NetworkUtil;

import java.io.IOException;

public class InformationsRequestWithReplyHandler implements RequestHandler {
    private final NetworkController networkController;

    public InformationsRequestWithReplyHandler(NetworkController networkController) {
        this.networkController = networkController;
    }

    @Override
    public void handle(ObjectSender objectSender) {
        networkController.saveInformations((PublicSecurityInformations) objectSender.getObject());
        try {
            NetworkUtil.send(
                    objectSender.getIP_sender(),
                    objectSender.getPORT_sender(),
                    new ObjectSender(
                            networkController.getMyPublicInformations().getNetworkContactInformation().getIp(),
                            networkController.getMyPublicInformations().getNetworkContactInformation().getPort(),
                            networkController.getMyPublicInformations(),
                            PublicSecurityInformations.class
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
