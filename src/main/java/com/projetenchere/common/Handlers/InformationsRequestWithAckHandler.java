package com.projetenchere.common.Handlers;

import com.projetenchere.common.Models.Network.Communication.Acknowledgment;
import com.projetenchere.common.Models.Network.Communication.Informations.PublicSecurityInformations;
import com.projetenchere.common.Models.Network.Communication.ObjectReceived;
import com.projetenchere.common.Models.Network.Sendable.ObjectSender;
import com.projetenchere.common.Controllers.NetworkController;
import com.projetenchere.common.Utils.NetworkUtil;

import java.io.IOException;

public class InformationsRequestWithAckHandler implements RequestHandler {
    private final NetworkController networkController;

    public InformationsRequestWithAckHandler(NetworkController networkController) {
        this.networkController = networkController;
    }

    @Override
    public void handle(ObjectReceived objectReceived) {
        networkController.saveInformations((PublicSecurityInformations) objectReceived.getObjectSended().getObject());
        try {
            NetworkUtil.send(
                    objectReceived.getObjectSended().getIP_sender(),
                    objectReceived.getObjectSended().getPORT_sender(),
                    new ObjectSender(
                            networkController.getMyPublicInformations().getNetworkContactInformation().ip(),
                            networkController.getMyPublicInformations().getNetworkContactInformation().port(),
                            new Acknowledgment("OK"),
                            Acknowledgment.class
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
