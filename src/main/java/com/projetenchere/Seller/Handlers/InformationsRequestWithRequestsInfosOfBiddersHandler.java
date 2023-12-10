package com.projetenchere.Seller.Handlers;

import com.projetenchere.common.Controllers.NetworkController;
import com.projetenchere.common.Handlers.RequestHandler;
import com.projetenchere.common.Models.Network.Communication.Informations.PublicSecurityInformations;
import com.projetenchere.common.Models.Network.Communication.ObjectReceived;
import com.projetenchere.common.Models.Network.Sendable.DataWrapper;
import com.projetenchere.common.Utils.NetworkUtil;

import java.io.IOException;

public class InformationsRequestWithRequestsInfosOfBiddersHandler implements RequestHandler {
    private final NetworkController networkController;

    public InformationsRequestWithRequestsInfosOfBiddersHandler(NetworkController networkController) {
        this.networkController = networkController;
    }

    @Override
    public void handle(ObjectReceived objectReceived) {
        networkController.saveInformations((PublicSecurityInformations) objectReceived.getObjectSended().getObject());
        try {
            NetworkUtil.send(
                    objectReceived.getObjectSended().getIP_sender(),
                    objectReceived.getObjectSended().getPORT_sender(),
                    new DataWrapper(
                            networkController.getMyPublicInformations().getNetworkContactInformation().ip(),
                            networkController.getMyPublicInformations().getNetworkContactInformation().port(),
                            "RequestsInfosOfBidders",
                            String.class
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
