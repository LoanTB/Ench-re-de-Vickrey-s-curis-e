package com.projetenchere.Seller.Controller.Network.Handlers;

import com.projetenchere.Seller.Controller.Network.SellerNetworkController;
import com.projetenchere.common.Models.Network.Communication.SecurityInformations;
import com.projetenchere.common.Models.Network.RequestHandler;
import com.projetenchere.common.Models.Network.Sendable.ObjectSender;

public class ManagerInformationsRequestHandler implements RequestHandler {
    private final SellerNetworkController networkController;

    public ManagerInformationsRequestHandler(SellerNetworkController networkController) {
        this.networkController = networkController;
    }

    @Override
    public void handle(ObjectSender objectSender) {
        networkController.setManagerInformations((SecurityInformations) objectSender.getObject());
    }
}
