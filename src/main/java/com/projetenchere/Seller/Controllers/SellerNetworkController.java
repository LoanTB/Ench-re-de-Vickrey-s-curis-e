package com.projetenchere.Seller.Controllers;

import com.projetenchere.Seller.Handlers.EncryptedPricesRequestHandler;
import com.projetenchere.Seller.Handlers.InformationsRequestWithRequestsInfosOfBiddersHandler;
import com.projetenchere.Seller.Handlers.WinnerRequestHandler;
import com.projetenchere.common.Models.Encrypted.EncryptedOffer;
import com.projetenchere.common.Models.Identity;
import com.projetenchere.common.Models.Network.Communication.Acknowledgment;
import com.projetenchere.common.Models.Network.Communication.Informations.NetworkContactInformation;
import com.projetenchere.common.Models.Network.Communication.Informations.PublicSecurityInformations;
import com.projetenchere.common.Handlers.InformationsRequestWithAckHandler;
import com.projetenchere.common.Controllers.NetworkController;
import com.projetenchere.common.Handlers.RequestHandler;
import com.projetenchere.common.Models.Network.Communication.ObjectReceived;
import com.projetenchere.common.Models.Network.Communication.Winner;

import java.util.UUID;

public class SellerNetworkController extends NetworkController {
    private final SellerController controller;

    public SellerNetworkController(SellerController controller) throws Exception {
        this.controller = controller;
        saveInformations(new PublicSecurityInformations(new Identity("Manager","Manager"),new NetworkContactInformation("127.0.0.1",24683),null,null));
    }

    @Override
    protected RequestHandler determineSpecificsHandler(ObjectReceived objectReceived) {
        if (objectReceived.getObjectSended().getObjectClass().equals(PublicSecurityInformations.class)
                && !alreadyKnowTheInformation((PublicSecurityInformations) objectReceived.getObjectSended().getObject())
                && ((PublicSecurityInformations) objectReceived.getObjectSended().getObject()).getIdentity().getId().equals("Manager")){
            controller.getUi().tellSuccessfulSecuringOfTheCommunicationChannelWithTheManager();
            return new InformationsRequestWithRequestsInfosOfBiddersHandler(this);
        }
        if (objectReceived.getObjectSended().getObjectClass().equals(PublicSecurityInformations.class)
                && !alreadyKnowTheInformation((PublicSecurityInformations) objectReceived.getObjectSended().getObject())) {
            controller.getUi().tellReceivingInformationOf(((PublicSecurityInformations) objectReceived.getObjectSended().getObject()).getIdentity().getId(),((PublicSecurityInformations) objectReceived.getObjectSended().getObject()).getIdentity().getType());
            return new InformationsRequestWithAckHandler(this);
        }
        if (objectReceived.getObjectSended().getObjectClass().equals(Acknowledgment.class)
                && ((Acknowledgment) objectReceived.getObjectSended().getObject()).getStatus().equals("OK")
                && isAuthenticatedByAnyKnowledgeOfType("Manager",objectReceived)){
            controller.getUi().tellManagerConfirmsReceipt();
            return null;
        }
        if (objectReceived.getObjectSended().getObjectClass().equals(EncryptedOffer.class)
                 && isAuthenticatedByAnyKnowledgeOfType("Bidder",objectReceived)
                 && controller.auctionInProgress()) {
            controller.getUi().tellReceiptOfferByBidder(objectReceived.getAuthenticationStatus().authorOfSignature().getId());
            return new EncryptedPricesRequestHandler(controller);
        }
        if (objectReceived.getObjectSended().getObjectClass().equals(Winner.class)
                && isAuthenticatedByType("Manager",objectReceived)
                && !controller.auctionInProgress()) {
            controller.getUi().tellReceiptBidResult(((Winner) objectReceived.getObjectSended().getObject()).getBidId());
            return new WinnerRequestHandler(controller);
        }
        return null;
    }
}