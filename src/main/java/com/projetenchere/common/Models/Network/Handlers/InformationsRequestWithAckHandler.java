package com.projetenchere.common.Models.Network.Handlers;

import com.projetenchere.common.Models.Network.Communication.Acknowledgment;
import com.projetenchere.common.Models.Network.Communication.Informations.PublicSecurityInformations;
import com.projetenchere.common.Models.Network.RequestHandler;
import com.projetenchere.common.Models.Network.Sendable.ObjectSender;
import com.projetenchere.common.Models.Network.NetworkController;
import com.projetenchere.common.Utils.NetworkUtil;

import java.io.IOException;

public class InformationsRequestWithAckHandler implements RequestHandler {
    private final NetworkController networkController;

    public InformationsRequestWithAckHandler(NetworkController networkController) {
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
                            networkController.getMyInformations().getNetworkContactInformation().getIp(),
                            networkController.getMyInformations().getNetworkContactInformation().getPort(),
                            new Acknowledgment("OK"),
                            Acknowledgment.class
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
