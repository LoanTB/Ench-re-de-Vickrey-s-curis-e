package com.projetenchere.Manager.Controller.Network;

import com.projetenchere.Manager.Controller.ManagerController;
import com.projetenchere.Manager.Controller.Network.Handlers.InitPackageRequestHandler;
import com.projetenchere.Manager.Controller.Network.Handlers.PriceDecryptionRequestHandler;
import com.projetenchere.common.Models.Bid;
import com.projetenchere.common.Models.Encrypted.EncryptedPrices;
import com.projetenchere.common.Models.Network.Communication.NetworkContactInformation;
import com.projetenchere.common.Models.Network.NetworkController;
import com.projetenchere.common.Models.Network.RequestHandler;
import com.projetenchere.common.Models.Network.Sendable.ObjectSender;
import com.projetenchere.common.Utils.Network.NetworkUtil;

import java.io.IOException;
import java.net.UnknownHostException;

public class ManagerNetworkController extends NetworkController {
    private final ManagerController controller;

    public ManagerNetworkController(ManagerController controller) throws UnknownHostException {
        this.controller = controller;
        myNCI = new NetworkContactInformation(NetworkUtil.getMyIP(),24683);
    }

    public String getManagerIp() {
        return myNCI.getIp();
    }

    public int getManagerPort() {
        return myNCI.getPort();
    }

    @Override
    public RequestHandler determineSpecificsHandler(ObjectSender objectSender) {
        if (objectSender.getObject().equals("InitPackageRequest")) {
            return new InitPackageRequestHandler(controller.getCurrentBids(),myNCI);
        }
        if (objectSender.getObjectClass() == EncryptedPrices.class && controller.getCurrentBids().isOver(((EncryptedPrices)objectSender.getObject()).getBidId())) {
            return new PriceDecryptionRequestHandler(controller,myNCI);
        }
        return null;
    }

    public void sendBidToSeller(Bid bid) throws IOException {
        NetworkUtil.send(bid.getSellerIp(),
                bid.getSellerPort(),
                new ObjectSender(getManagerIp(),
                        getManagerPort(),
                        bid,
                        Bid.class
                )
        );
    }
}