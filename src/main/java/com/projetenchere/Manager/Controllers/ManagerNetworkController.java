package com.projetenchere.Manager.Controllers;

import com.projetenchere.Manager.Handlers.InitPackageRequestHandler;
import com.projetenchere.Manager.Handlers.PriceDecryptionRequestHandler;
import com.projetenchere.common.Models.Bid;
import com.projetenchere.common.Models.Encrypted.EncryptedPrices;
import com.projetenchere.common.Models.Network.Communication.Informations.NetworkContactInformation;
import com.projetenchere.common.Models.Network.Communication.Informations.PrivateSecurityInformations;
import com.projetenchere.common.Models.Network.Communication.Informations.PublicSecurityInformations;
import com.projetenchere.common.Handlers.InformationsRequestWithReplyHandler;
import com.projetenchere.common.Controllers.NetworkController;
import com.projetenchere.common.Handlers.RequestHandler;
import com.projetenchere.common.Models.Network.Communication.ObjectReceived;
import com.projetenchere.common.Models.Network.Sendable.ObjectSender;
import com.projetenchere.common.Utils.EncryptionUtil;
import com.projetenchere.common.Utils.NetworkUtil;

import java.io.IOException;

public class ManagerNetworkController extends NetworkController {
    private final ManagerController controller;

    public ManagerNetworkController(ManagerController controller) throws Exception {
        this.controller = controller;
        myInformations = new PrivateSecurityInformations(new NetworkContactInformation(NetworkUtil.getMyIP(),24683),EncryptionUtil.generateKeyPair(),EncryptionUtil.generateKeyPair());
    }

    @Override
    public RequestHandler determineSpecificsHandler(ObjectReceived objectReceived) {
        if (objectReceived.getObjectSended().getObjectClass().equals(PublicSecurityInformations.class) &&
                !informationContainsPublicKeys(((PublicSecurityInformations) objectReceived.getObjectSended().getObject()).getId())) {
            return new InformationsRequestWithReplyHandler(this);
        }
        if (objectReceived.getObjectSended().getObject().equals("InitPackageRequest")) {
            return new InitPackageRequestHandler(controller.getCurrentBids(),myInformations.getNetworkContactInformation());
        }
        if (objectReceived.getObjectSended().getObjectClass() == EncryptedPrices.class && controller.getCurrentBids().isOver(((EncryptedPrices)objectReceived.getObjectSended().getObject()).getBidId())) {
            return new PriceDecryptionRequestHandler(controller,myInformations.getNetworkContactInformation());
        }
        return null;
    }

    public void sendMySI(String id) throws Exception {
        sendTo(id, getMyPublicInformations());
    }

    public void sendBidToSeller(Bid bid) throws IOException {
        NetworkUtil.send(bid.getSellerIp(),
                bid.getSellerPort(),
                new ObjectSender(
                        myInformations.getNetworkContactInformation().getIp(),
                        myInformations.getNetworkContactInformation().getPort(),
                        bid,
                        Bid.class
                )
        );
    }
}