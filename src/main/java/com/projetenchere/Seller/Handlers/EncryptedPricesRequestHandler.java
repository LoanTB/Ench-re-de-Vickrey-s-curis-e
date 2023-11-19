package com.projetenchere.Seller.Handlers;

import com.projetenchere.Seller.Controllers.SellerController;
import com.projetenchere.common.Models.Encrypted.EncryptedOffer;
import com.projetenchere.common.Handlers.RequestHandler;
import com.projetenchere.common.Models.Network.Sendable.ObjectSender;

public class EncryptedPricesRequestHandler implements RequestHandler {
    private final SellerController sellerController;

    public EncryptedPricesRequestHandler(SellerController sellerController) {
        this.sellerController = sellerController;
    }

    @Override
    public void handle(ObjectSender objectSender) {
        sellerController.saveEncryptedOffer((EncryptedOffer) objectSender.getObject(),objectSender.getIP_sender(),objectSender.getPORT_sender());
    }
}
