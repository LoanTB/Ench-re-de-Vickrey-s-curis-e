package com.projetenchere.Seller.Controller.Network;

import com.projetenchere.Seller.Controller.Network.Handlers.EncryptedPricesRequestHandler;
import com.projetenchere.Seller.Controller.Network.Handlers.WinnerRequestHandler;
import com.projetenchere.Seller.Controller.SellerController;
import com.projetenchere.common.Models.Encrypted.EncryptedOffer;
import com.projetenchere.common.Models.Encrypted.EncryptedPrices;
import com.projetenchere.common.Models.Network.Communication.Informations.NetworkContactInformation;
import com.projetenchere.common.Models.Network.Communication.Informations.PrivateSecurityInformations;
import com.projetenchere.common.Models.Network.Communication.Informations.PublicSecurityInformations;
import com.projetenchere.common.Models.Network.Handlers.InformationsRequestWithAckHandler;
import com.projetenchere.common.Models.Network.NetworkController;
import com.projetenchere.common.Models.Network.RequestHandler;
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
    protected RequestHandler determineSpecificsHandler(ObjectSender objectSender) {
        if (objectSender.getObjectClass().equals(PublicSecurityInformations.class) &&
                !informationContainsPublicKey(((PublicSecurityInformations) objectSender.getObject()).getId())) {
            return new InformationsRequestWithAckHandler(this);
        }
        if (objectSender.getObjectClass().equals(EncryptedOffer.class) && controller.auctionInProgress()) {
            return new EncryptedPricesRequestHandler(controller);
        }
        if (objectSender.getObjectClass().equals(Winner.class) && !controller.auctionInProgress()) {
            return new WinnerRequestHandler(controller);
        }
        return null;
    }

    public void sendEncryptedPrices(EncryptedPrices encryptedPrices) throws IOException {
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

    public void sendMySI(String id) throws IOException {
        sendTo(id,getMyInformations());
    }
}