package com.projetenchere.Manager.Controllers;

import com.projetenchere.Manager.Handlers.*;
import com.projetenchere.common.Models.Encrypted.EncryptedPrices;
import com.projetenchere.common.Models.Identity;
import com.projetenchere.common.Models.Network.Communication.Informations.NetworkContactInformation;
import com.projetenchere.common.Models.Network.Communication.Informations.PrivateSecurityInformations;
import com.projetenchere.common.Models.Network.Communication.Informations.PublicSecurityInformations;
import com.projetenchere.common.Controllers.NetworkController;
import com.projetenchere.common.Handlers.RequestHandler;
import com.projetenchere.common.Models.Network.Communication.ObjectReceived;
import com.projetenchere.common.Utils.EncryptionUtil;
import com.projetenchere.common.Utils.NetworkUtil;

import java.io.IOException;

public class ManagerNetworkController extends NetworkController {
    private final ManagerController controller;

    public ManagerNetworkController(ManagerController controller) throws Exception {
        this.controller = controller;
        myInformations = new PrivateSecurityInformations(new Identity("Manager","Manager"),new NetworkContactInformation(NetworkUtil.getMyIP(),24683),EncryptionUtil.generateKeyPair(),EncryptionUtil.generateKeyPair());
    }

    @Override
    public RequestHandler determineSpecificsHandler(ObjectReceived objectReceived) {
        if (objectReceived.getObjectSended().getObjectClass().equals(PublicSecurityInformations.class)
                && !alreadyKnowTheInformation((PublicSecurityInformations) objectReceived.getObjectSended().getObject())
                && ((PublicSecurityInformations) objectReceived.getObjectSended().getObject()).getIdentity().getType().equals("Seller")){
            // TODO : Afficher avec ui "Connexion d'un nouveau vendeur (ID)"
            return new InformationsRequestWithReplyHandler(this);// TODO (LEVELUP) : envoie des informations aux enchérisseur sur la nouvelle offre qui viens d'apparaitre
        }
        if (objectReceived.getObjectSended().getObjectClass().equals(PublicSecurityInformations.class)
                && !alreadyKnowTheInformation((PublicSecurityInformations) objectReceived.getObjectSended().getObject())
                && ((PublicSecurityInformations) objectReceived.getObjectSended().getObject()).getIdentity().getType().equals("Bidder")){
            // TODO : Afficher avec ui "Connexion d'un nouvel enchérisseur (ID), notification des vendeurs sur le nouvel enchérisseur potentiel"
            return new InformationsRequestWithReplyAndSendBidderInfosToSellersHandler(this);
        }
        if (objectReceived.getObjectSended().getObjectClass().equals(String.class)
                && objectReceived.getObjectSended().getObject().equals("RequestsInfosOfBidders")
                && isAuthenticatedByType("Seller",objectReceived)){
            // TODO : Afficher avec ui "Demande des informations sur les enchérisseurs actuellement connectés par le vendeur ID"
            return new BiddersInfosRequestHandler(this);
        }
        if (objectReceived.getObjectSended().getObjectClass().equals(String.class)
                && objectReceived.getObjectSended().getObject().equals("InitPackageRequest")
                && isAuthenticatedByType("Bidder",objectReceived)) {
            // TODO : Afficher avec ui "Demande des enchères actuelles par l'enchérisseur ID"
            return new InitPackageRequestHandler(this,controller.getCurrentBids());
        }
        if (objectReceived.getObjectSended().getObjectClass() == EncryptedPrices.class
                && controller.getCurrentBids().isOver(((EncryptedPrices)objectReceived.getObjectSended().getObject()).getBidId())
                && isAuthenticatedByType("Seller",objectReceived)) {
            // TODO : Afficher avec ui "Demande de détermination du gagnant de l'enchère ID par le vendeur ID"
            return new PriceDecryptionRequestHandler(controller,this);
        }
        return null;
    }
}