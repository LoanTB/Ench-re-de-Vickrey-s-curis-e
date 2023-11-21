package com.projetenchere.Bidder.Controllers;

import com.projetenchere.Bidder.Handlers.CurrentBidsHandler;
import com.projetenchere.Bidder.Handlers.CurrentBidsPublicKeysHandler;
import com.projetenchere.Bidder.Handlers.WinStatusRequestHandler;
import com.projetenchere.common.Models.Identity;
import com.projetenchere.common.Models.Network.Communication.CurrentBids;
import com.projetenchere.common.Models.Network.Communication.CurrentBidsPublicKeys;
import com.projetenchere.common.Models.Network.Communication.Informations.NetworkContactInformation;
import com.projetenchere.common.Models.Network.Communication.Informations.PublicSecurityInformations;
import com.projetenchere.common.Models.Network.Communication.ObjectReceived;
import com.projetenchere.common.Models.Network.Communication.WinStatus;
import com.projetenchere.common.Handlers.InformationsRequestWithAckHandler;
import com.projetenchere.common.Handlers.RequestHandler;
import com.projetenchere.common.Controllers.NetworkController;

public class BidderNetworkController extends NetworkController {
    private final BidderController controller;

    public BidderNetworkController(BidderController bidderController) {
        controller = bidderController;
        saveInformations(new PublicSecurityInformations(new Identity("Manager","Manager"),new NetworkContactInformation("127.0.0.1",24683),null,null));
    }

    @Override
    public RequestHandler determineSpecificsHandler(ObjectReceived objectReceived) {
        if (objectReceived.getObjectSended().getObjectClass().equals(PublicSecurityInformations.class)
                && !alreadyKnowTheInformation((PublicSecurityInformations) objectReceived.getObjectSended().getObject())) {
            controller.getUi().tellReceivingInformationOf(((PublicSecurityInformations) objectReceived.getObjectSended().getObject()).getIdentity().getId(),((PublicSecurityInformations) objectReceived.getObjectSended().getObject()).getIdentity().getType());
            return new InformationsRequestWithAckHandler(this);
        }
        if (objectReceived.getObjectSended().getObjectClass().equals(CurrentBids.class)
                && isAuthenticatedByType("Manager",objectReceived)) {
            controller.getUi().tellReceiptOfCurrentBids();
            return new CurrentBidsHandler(controller);
        }
        if (objectReceived.getObjectSended().getObjectClass().equals(CurrentBidsPublicKeys.class)
                && isAuthenticatedByType("Manager",objectReceived)) {
            controller.getUi().tellReceiptOfEncryptionKeysForCurrentBids();
            return new CurrentBidsPublicKeysHandler(controller);
        }
        if (objectReceived.getObjectSended().getObjectClass().equals(WinStatus.class)
                && isAuthenticatedByType("Seller",objectReceived)
                && controller.getParticipatedBid().contains(((WinStatus) objectReceived.getObjectSended().getObject()).getBidId())) {
            controller.getUi().tellReceiptOfBidResult(((WinStatus) objectReceived.getObjectSended().getObject()).getBidId());
            return new WinStatusRequestHandler(controller);
        }
        return null;
    }
}
