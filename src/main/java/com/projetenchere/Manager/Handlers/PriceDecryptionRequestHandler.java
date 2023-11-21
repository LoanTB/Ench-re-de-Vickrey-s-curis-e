package com.projetenchere.Manager.Handlers;

import com.projetenchere.Manager.Controllers.ManagerController;
import com.projetenchere.common.Controllers.NetworkController;
import com.projetenchere.common.Models.Encrypted.EncryptedPrices;
import com.projetenchere.common.Models.Network.Communication.ObjectReceived;
import com.projetenchere.common.Models.Network.Communication.Winner;
import com.projetenchere.common.Models.Network.Communication.Informations.NetworkContactInformation;
import com.projetenchere.common.Handlers.RequestHandler;
import com.projetenchere.common.Models.Network.Sendable.ObjectSender;
import com.projetenchere.common.Utils.NetworkUtil;

public class PriceDecryptionRequestHandler implements RequestHandler {
    private final ManagerController managerController;
    private final NetworkController networkController;

    public PriceDecryptionRequestHandler(ManagerController managerController, NetworkController networkController) {
        this.managerController = managerController;
        this.networkController = networkController;
    }

    @Override
    public void handle(ObjectReceived objectReceived) throws Exception {
        networkController.sendTo(
                objectReceived.getAuthenticationStatus().authorOfSignature().getId(),
                managerController.processPrices((EncryptedPrices) objectReceived.getObjectSended().getObject())
        );
    }
}
