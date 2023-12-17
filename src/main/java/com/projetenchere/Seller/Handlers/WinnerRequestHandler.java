package com.projetenchere.Seller.Handlers;

import com.projetenchere.Seller.Controllers.SellerController;
import com.projetenchere.common.Models.Network.Communication.ObjectReceived;
import com.projetenchere.common.Models.Network.Communication.Winner;
import com.projetenchere.common.Handlers.RequestHandler;

public class WinnerRequestHandler implements RequestHandler {
    private final SellerController controller;

    public WinnerRequestHandler(SellerController controller) {
        this.controller = controller;
    }

    @Override
    public void handle(ObjectReceived objectReceived) {
        controller.setWinner((Winner) objectReceived.getObjectSended().getObject());
    }
}
