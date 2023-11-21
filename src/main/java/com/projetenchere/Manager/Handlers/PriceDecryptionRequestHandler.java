package com.projetenchere.Manager.Handlers;

import com.projetenchere.Manager.Controllers.ManagerController;
import com.projetenchere.common.Controllers.NetworkController;
import com.projetenchere.common.Models.Encrypted.EncryptedPrices;
import com.projetenchere.common.Models.Network.Communication.ObjectReceived;
import com.projetenchere.common.Handlers.RequestHandler;

public class PriceDecryptionRequestHandler implements RequestHandler {
    private final ManagerController managerController;
    private final NetworkController networkController;

    public PriceDecryptionRequestHandler(ManagerController managerController, NetworkController networkController) {
        this.managerController = managerController;
        this.networkController = networkController;
    }

    @Override
    public void handle(ObjectReceived objectReceived) throws Exception {
        EncryptedPrices encryptedPrices = (EncryptedPrices) objectReceived.getObjectSended().getObject();
        networkController.sendTo(
                objectReceived.getAuthenticationStatus().authorOfSignature().getId(),
                managerController.processPrices(encryptedPrices,managerController.getCurrentBidsPrivateKeys().getKeyOfBid(encryptedPrices.getBidId()).getPrivate())
        );
    }
}
