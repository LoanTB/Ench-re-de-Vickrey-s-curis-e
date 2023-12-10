package com.projetenchere.Manager.Handlers;

import com.projetenchere.common.Controllers.NetworkController;
import com.projetenchere.common.Handlers.RequestHandler;
import com.projetenchere.common.Models.Network.Communication.Informations.PublicSecurityInformations;
import com.projetenchere.common.Models.Network.Communication.ObjectReceived;
import com.projetenchere.common.Models.Network.Sendable.DataWrapper;
import com.projetenchere.common.Utils.NetworkUtil;

import java.io.IOException;

public class InformationsRequestWithReplyAndSendBidderInfosToSellersHandler implements RequestHandler {
    private final NetworkController networkController;

    public InformationsRequestWithReplyAndSendBidderInfosToSellersHandler(NetworkController networkController) {
        this.networkController = networkController;
    }

    @Override
    public void handle(ObjectReceived objectReceived) throws Exception {
        networkController.saveInformations((PublicSecurityInformations) objectReceived.getObjectSended().getObject());
        try {
            NetworkUtil.send(
                    objectReceived.getObjectSended().getIP_sender(),
                    objectReceived.getObjectSended().getPORT_sender(),
                    new DataWrapper(
                            networkController.getMyPublicInformations().getNetworkContactInformation().ip(),
                            networkController.getMyPublicInformations().getNetworkContactInformation().port(),
                            networkController.getMyPublicInformations(),
                            PublicSecurityInformations.class
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        synchronized (this) {
            for (PublicSecurityInformations publicSecurityInformations:networkController.getAnyInformationsOfType("Seller")){
                networkController.sendTo(publicSecurityInformations.getIdentity().getId(),(PublicSecurityInformations) objectReceived.getObjectSended().getObject());
                wait(100);
            }
        }

    }
}
