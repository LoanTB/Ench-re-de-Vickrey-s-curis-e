package com.projetenchere.Manager.Handlers;

import com.projetenchere.Manager.Controllers.ManagerController;
import com.projetenchere.common.Models.Encrypted.EncryptedPrices;
import com.projetenchere.common.Models.Network.Communication.ObjectReceived;
import com.projetenchere.common.Models.Network.Communication.Winner;
import com.projetenchere.common.Models.Network.Communication.Informations.NetworkContactInformation;
import com.projetenchere.common.Handlers.RequestHandler;
import com.projetenchere.common.Models.Network.Sendable.ObjectSender;
import com.projetenchere.common.Utils.NetworkUtil;

public class PriceDecryptionRequestHandler implements RequestHandler {
    private final ManagerController managerController;
    private final NetworkContactInformation managerNCI;

    public PriceDecryptionRequestHandler(ManagerController managerController, NetworkContactInformation managerNCI) {
        this.managerController = managerController;
        this.managerNCI = managerNCI;
    }

    @Override
    public void handle(ObjectReceived objectReceived) {
        try {
            NetworkUtil.send(
                    objectReceived.getObjectSended().getIP_sender(),
                    objectReceived.getObjectSended().getPORT_sender(),
                    new ObjectSender(
                            managerNCI.ip(),
                            managerNCI.port(),
                            managerController.processPrices((EncryptedPrices) objectReceived.getObjectSended().getObject()),
                            Winner.class
                    )
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
