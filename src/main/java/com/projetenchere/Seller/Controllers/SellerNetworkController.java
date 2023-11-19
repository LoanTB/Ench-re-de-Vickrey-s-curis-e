package com.projetenchere.Seller.Controllers;

import com.projetenchere.Seller.Handlers.EncryptedPricesRequestHandler;
import com.projetenchere.Seller.Handlers.WinnerRequestHandler;
import com.projetenchere.common.Models.Encrypted.EncryptedOffer;
import com.projetenchere.common.Models.Encrypted.EncryptedPrices;
import com.projetenchere.common.Models.Network.Communication.Informations.NetworkContactInformation;
import com.projetenchere.common.Models.Network.Communication.Informations.PrivateSecurityInformations;
import com.projetenchere.common.Models.Network.Communication.Informations.PublicSecurityInformations;
import com.projetenchere.common.Handlers.InformationsRequestWithAckHandler;
import com.projetenchere.common.Controllers.NetworkController;
import com.projetenchere.common.Handlers.RequestHandler;
import com.projetenchere.common.Models.Network.Communication.ObjectReceived;
import com.projetenchere.common.Models.Network.Sendable.ObjectSender;
import com.projetenchere.common.Models.Network.Communication.Winner;
import com.projetenchere.common.Utils.EncryptionUtil;
import com.projetenchere.common.Utils.NetworkUtil;

import java.io.IOException;
import java.util.List;

public class SellerNetworkController extends NetworkController {
    private final SellerController controller;

    public SellerNetworkController(SellerController controller) throws Exception {
        this.controller = controller;
        myInformations = new PrivateSecurityInformations(new NetworkContactInformation("127.0.0.1",24683),EncryptionUtil.generateKeyPair(),EncryptionUtil.generateKeyPair());
    }

    @Override
    protected RequestHandler determineSpecificsHandler(ObjectReceived objectReceived) {
        if (objectReceived.getObjectSended().getObjectClass().equals(PublicSecurityInformations.class) &&
                !informationContainsPublicKeys(((PublicSecurityInformations) objectReceived.getObjectSended().getObject()).getId())) {
            return new InformationsRequestWithAckHandler(this);
        }
        if (objectReceived.getObjectSended().getObjectClass().equals(EncryptedOffer.class) && controller.auctionInProgress()) {
            return new EncryptedPricesRequestHandler(controller);
        }
        if (objectReceived.getObjectSended().getObjectClass().equals(Winner.class) && !controller.auctionInProgress()) {
            return new WinnerRequestHandler(controller);
        }
        return null;
    }

    public void sendEncryptedPrices(EncryptedPrices encryptedPrices) throws Exception {
        sendTo("Manager",encryptedPrices);
    }

    public void sendResults(List<String> ips, List<Integer> ports, List<Double> results) throws IOException {
        for (int i=0;i< ips.size();i++){
            NetworkUtil.send(
                    ips.get(i),
                    ports.get(i),
                    new ObjectSender(
                            myInformations.getNetworkContactInformation().getIp(),
                            myInformations.getNetworkContactInformation().getPort(),
                            results.get(i),
                            results.get(i).getClass()
                    )
            );
        }
    }

    public void sendMySI(String id) throws Exception {
        sendTo(id, getMyPublicInformations());
    }
}