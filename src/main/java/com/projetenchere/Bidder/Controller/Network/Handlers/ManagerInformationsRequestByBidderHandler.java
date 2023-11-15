package com.projetenchere.Bidder.Controller.Network.Handlers;

import com.projetenchere.Bidder.Controller.Network.BidderNetworkController;
import com.projetenchere.Seller.Controller.Network.SellerNetworkController;
import com.projetenchere.common.Models.Network.Communication.SecurityInformations;
import com.projetenchere.common.Models.Network.RequestHandler;
import com.projetenchere.common.Models.Network.Sendable.ObjectSender;

public class ManagerInformationsRequestByBidderHandler implements RequestHandler {
    private final BidderNetworkController networkController;

    public ManagerInformationsRequestByBidderHandler(BidderNetworkController networkController) {
        this.networkController = networkController;
    }

    @Override
    public void handle(ObjectSender objectSender) {
        networkController.setManagerInformations((SecurityInformations) objectSender.getObject());
    }
}
