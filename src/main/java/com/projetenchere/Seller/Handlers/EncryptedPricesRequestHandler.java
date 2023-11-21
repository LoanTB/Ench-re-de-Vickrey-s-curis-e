package com.projetenchere.Seller.Handlers;

import com.projetenchere.Seller.Controllers.SellerController;
import com.projetenchere.common.Models.Encrypted.EncryptedOffer;
import com.projetenchere.common.Handlers.RequestHandler;
import com.projetenchere.common.Models.Network.Communication.ObjectReceived;
import com.projetenchere.common.Models.Network.Sendable.ObjectSender;

public class EncryptedPricesRequestHandler implements RequestHandler {
    private final SellerController sellerController;

    public EncryptedPricesRequestHandler(SellerController sellerController) {
        this.sellerController = sellerController;
    }

    @Override
    public void handle(ObjectReceived objectReceived) {
        sellerController.saveEncryptedOffer(
                (EncryptedOffer) objectReceived.getObjectSended().getObject(),
                objectReceived.getAuthenticationStatus().authorOfSignature().getId(),
                objectReceived.getObjectSended().getIP_sender(),
                objectReceived.getObjectSended().getPORT_sender());
    }
}
