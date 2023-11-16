package com.projetenchere.Seller.Controller.Network;

import com.projetenchere.Seller.Controller.Network.Handlers.EncryptedPricesRequestHandler;
import com.projetenchere.Seller.Controller.Network.Handlers.WinnerRequestHandler;
import com.projetenchere.Seller.Controller.SellerController;
import com.projetenchere.common.Models.Encrypted.EncryptedOffer;
import com.projetenchere.common.Models.Encrypted.EncryptedPrices;
import com.projetenchere.common.Models.Network.Communication.NetworkContactInformation;
import com.projetenchere.common.Models.Network.Communication.SecurityInformations;
import com.projetenchere.common.Models.Network.Handlers.InformationsRequestHandler;
import com.projetenchere.common.Models.Network.NetworkController;
import com.projetenchere.common.Models.Network.RequestHandler;
import com.projetenchere.common.Models.Network.Sendable.ObjectSender;
import com.projetenchere.common.Models.Network.Communication.Winner;
import com.projetenchere.common.Utils.EncryptionUtil;
import com.projetenchere.common.Utils.Network.NetworkUtil;

import java.io.IOException;
import java.security.KeyPair;
import java.util.List;

public class SellerNetworkController extends NetworkController {
    private final SellerController controller;
    private final KeyPair keys = EncryptionUtil.generateKeyPair();

    public SellerNetworkController(SellerController controller) throws Exception {
        this.controller = controller;
        myNCI = new NetworkContactInformation(NetworkUtil.getMyIP(),24682);
        saveMyInformations(keys.getPublic());
        saveInformations(new SecurityInformations("Manager",new NetworkContactInformation("127.0.0.1",24683)));
    }

    @Override
    protected RequestHandler determineSpecificsHandler(ObjectSender objectSender) {
        if (objectSender.getObjectClass().equals(SecurityInformations.class) &&
                !informationContainsPublicKey(((SecurityInformations) objectSender.getObject()).getId())) {
            return new InformationsRequestHandler(this);
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
                            myNCI.getIp(),
                            myNCI.getPort(),
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