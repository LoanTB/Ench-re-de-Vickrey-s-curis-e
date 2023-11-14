package com.projetenchere.Manager.Controller.Network.Handlers;

import com.projetenchere.common.Models.Network.Communication.CurrentBids;
import com.projetenchere.common.Models.Network.NetworkContactInformation;
import com.projetenchere.common.Models.Network.RequestHandler;
import com.projetenchere.common.Models.Network.Sendable.ObjectSender;
import com.projetenchere.common.Utils.Network.NetworkUtil;

import java.io.IOException;

public class InitPackageRequestRequestHandler implements RequestHandler {
    private final CurrentBids currentCurrentBidsStarters;
    private final NetworkContactInformation managerNCI;

    public InitPackageRequestRequestHandler(CurrentBids currentCurrentBidsStarters, NetworkContactInformation managerNCI) {
        this.currentCurrentBidsStarters = currentCurrentBidsStarters;
        this.managerNCI = managerNCI;
    }

    @Override
    public void handle(ObjectSender objectSender) {
        try {
            NetworkUtil.send(
                    objectSender.getIP_sender(),
                    objectSender.getPORT_sender(),
                    new ObjectSender(
                            managerNCI.getIp(),
                            managerNCI.getPort(),
                            currentCurrentBidsStarters,
                            CurrentBids.class
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
