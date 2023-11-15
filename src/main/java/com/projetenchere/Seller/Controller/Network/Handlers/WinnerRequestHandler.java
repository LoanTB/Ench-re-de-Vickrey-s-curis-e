package com.projetenchere.Seller.Controller.Network.Handlers;

import com.projetenchere.Seller.Controller.SellerController;
import com.projetenchere.common.Models.Network.Communication.Winner;
import com.projetenchere.common.Models.Network.RequestHandler;
import com.projetenchere.common.Models.Network.Sendable.ObjectSender;

public class WinnerRequestHandler implements RequestHandler {
    private final SellerController controller;

    public WinnerRequestHandler(SellerController controller) {
        this.controller = controller;
    }

    @Override
    public void handle(ObjectSender objectSender) {
        controller.setWinner((Winner) objectSender.getObject());
    }
}
