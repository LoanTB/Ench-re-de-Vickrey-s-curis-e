package com.projetenchere.common.Models.Network.Handlers;

import com.projetenchere.common.Models.Network.Communication.CurrentBids;
import com.projetenchere.common.Models.Network.Communication.SecurityInformations;
import com.projetenchere.common.Models.Network.NetworkController;
import com.projetenchere.common.Models.Network.RequestHandler;
import com.projetenchere.common.Models.Network.Sendable.ObjectSender;
import com.projetenchere.common.Utils.Network.NetworkUtil;

import java.io.IOException;

public class InformationsRequestWithReplyHandler implements RequestHandler {
    private final NetworkController networkController;

    public InformationsRequestWithReplyHandler(NetworkController networkController) {
        this.networkController = networkController;
    }

    @Override
    public void handle(ObjectSender objectSender) {
        networkController.saveInformations((SecurityInformations) objectSender.getObject());
        try {
            NetworkUtil.send(
                    objectSender.getIP_sender(),
                    objectSender.getPORT_sender(),
                    new ObjectSender(
                            networkController.getMyInformations().getNetworkContactInformation().getIp(),
                            networkController.getMyInformations().getNetworkContactInformation().getPort(),
                            networkController.getMyInformations(),
                            SecurityInformations.class
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
