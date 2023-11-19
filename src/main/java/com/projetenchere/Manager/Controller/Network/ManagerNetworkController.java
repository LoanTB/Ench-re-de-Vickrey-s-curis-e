package com.projetenchere.Manager.Controller.Network;

import com.projetenchere.Manager.Controller.ManagerController;
import com.projetenchere.Manager.Controller.Network.Handlers.InitPackageRequestHandler;
import com.projetenchere.Manager.Controller.Network.Handlers.PriceDecryptionRequestHandler;
import com.projetenchere.common.Models.Bid;
import com.projetenchere.common.Models.Encrypted.EncryptedPrices;
import com.projetenchere.common.Models.Network.Communication.Informations.NetworkContactInformation;
import com.projetenchere.common.Models.Network.Communication.Informations.PrivateSecurityInformations;
import com.projetenchere.common.Models.Network.Communication.Informations.PublicSecurityInformations;
import com.projetenchere.common.Models.Network.Handlers.InformationsRequestWithReplyHandler;
import com.projetenchere.common.Models.Network.NetworkController;
import com.projetenchere.common.Models.Network.RequestHandler;
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
    public RequestHandler determineSpecificsHandler(ObjectSender objectSender) {
        if (objectSender.getObjectClass().equals(PublicSecurityInformations.class) &&
                !informationContainsPublicKey(((PublicSecurityInformations) objectSender.getObject()).getId())) {
            return new InformationsRequestWithReplyHandler(this);
        }
        if (objectSender.getObject().equals("InitPackageRequest")) {
            return new InitPackageRequestHandler(controller.getCurrentBids(),myInformations.getNetworkContactInformation());
        }
        if (objectSender.getObjectClass() == EncryptedPrices.class && controller.getCurrentBids().isOver(((EncryptedPrices)objectSender.getObject()).getBidId())) {
            return new PriceDecryptionRequestHandler(controller,myInformations.getNetworkContactInformation());
        }
        return null;
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