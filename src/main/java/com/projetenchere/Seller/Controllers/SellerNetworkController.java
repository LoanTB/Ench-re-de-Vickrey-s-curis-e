package com.projetenchere.Seller.Controllers;

import com.projetenchere.Seller.Handlers.EncryptedPricesRequestHandler;
import com.projetenchere.Seller.Handlers.WinnerRequestHandler;
import com.projetenchere.common.Models.Encrypted.EncryptedOffer;
import com.projetenchere.common.Models.Identity;
import com.projetenchere.common.Models.Network.Communication.Informations.NetworkContactInformation;
import com.projetenchere.common.Models.Network.Communication.Informations.PrivateSecurityInformations;
import com.projetenchere.common.Models.Network.Communication.Informations.PublicSecurityInformations;
import com.projetenchere.common.Handlers.InformationsRequestWithAckHandler;
import com.projetenchere.common.Controllers.NetworkController;
import com.projetenchere.common.Handlers.RequestHandler;
import com.projetenchere.common.Models.Network.Communication.ObjectReceived;
import com.projetenchere.common.Models.Network.Communication.Winner;
import com.projetenchere.common.Utils.EncryptionUtil;
import java.util.UUID;

public class SellerNetworkController extends NetworkController {
    private final SellerController controller;

    public SellerNetworkController(SellerController controller) throws Exception {
        this.controller = controller;
        myInformations = new PrivateSecurityInformations(new Identity(UUID.randomUUID().toString(),"Seller"),new NetworkContactInformation("127.0.0.1",24683),EncryptionUtil.generateKeyPair(),EncryptionUtil.generateKeyPair());
        saveInformations(new PublicSecurityInformations(new Identity("Manager","Manager"),new NetworkContactInformation("127.0.0.1",24683),null,null));
    }

    @Override
    protected RequestHandler determineSpecificsHandler(ObjectReceived objectReceived) {
        if (objectReceived.getObjectSended().getObjectClass().equals(PublicSecurityInformations.class)
                && !alreadyKnowTheInformation((PublicSecurityInformations) objectReceived.getObjectSended().getObject())) {
            return new InformationsRequestWithAckHandler(this);
        }
        if (objectReceived.getObjectSended().getObjectClass().equals(EncryptedOffer.class)
                 && isAuthenticatedByAnyKnowledgeOfType("Bidder",objectReceived)
                 && controller.auctionInProgress()) {
            return new EncryptedPricesRequestHandler(controller);
        }
        if (objectReceived.getObjectSended().getObjectClass().equals(Winner.class)
                && isAuthenticatedByType("Manager",objectReceived)
                && !controller.auctionInProgress()) {
            return new WinnerRequestHandler(controller);
        }
        return null;
    }
}