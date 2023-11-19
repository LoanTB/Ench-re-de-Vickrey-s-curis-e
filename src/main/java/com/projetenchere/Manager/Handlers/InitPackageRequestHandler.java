package com.projetenchere.Manager.Handlers;

import com.projetenchere.common.Models.Network.Communication.CurrentBids;
import com.projetenchere.common.Models.Network.Communication.Informations.NetworkContactInformation;
import com.projetenchere.common.Handlers.RequestHandler;
import com.projetenchere.common.Models.Network.Communication.ObjectReceived;
import com.projetenchere.common.Models.Network.Sendable.ObjectSender;
import com.projetenchere.common.Utils.NetworkUtil;

import java.io.IOException;

public class InitPackageRequestHandler implements RequestHandler {
    private final CurrentBids currentCurrentBidsStarters;
    private final NetworkContactInformation managerNCI;

    public InitPackageRequestHandler(CurrentBids currentCurrentBidsStarters, NetworkContactInformation managerNCI) {
        this.currentCurrentBidsStarters = currentCurrentBidsStarters;
        this.managerNCI = managerNCI;
    }

    //TODO : Ajouter des packagesRequests

    @Override
    public void handle(ObjectReceived objectReceived) {
        try {
            NetworkUtil.send(
                    objectReceived.getObjectSended().getIP_sender(),
                    objectReceived.getObjectSended().getPORT_sender(),
                    new ObjectSender(
                            managerNCI.ip(),
                            managerNCI.port(),
                            currentCurrentBidsStarters,
                            CurrentBids.class
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
