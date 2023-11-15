package com.projetenchere.Manager.Controller.Network.Handlers;

import com.projetenchere.Manager.Controller.ManagerController;
import com.projetenchere.common.Models.Encrypted.EncryptedPrices;
import com.projetenchere.common.Models.Network.Communication.Winner;
import com.projetenchere.common.Models.Network.NetworkContactInformation;
import com.projetenchere.common.Models.Network.RequestHandler;
import com.projetenchere.common.Models.Network.Sendable.ObjectSender;
import com.projetenchere.common.Utils.Network.NetworkUtil;

public class PriceDecryptionRequestHandler implements RequestHandler {
    private final ManagerController managerController;
    private final NetworkContactInformation managerNCI;

    public PriceDecryptionRequestHandler(ManagerController managerController, NetworkContactInformation managerNCI) {
        this.managerController = managerController;
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
                            managerController.processPrices((EncryptedPrices) objectSender.getObject()),
                            Winner.class
                    )
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
