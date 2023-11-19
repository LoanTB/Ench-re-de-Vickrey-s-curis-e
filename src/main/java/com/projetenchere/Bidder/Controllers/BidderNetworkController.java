package com.projetenchere.Bidder.Controllers;

import com.projetenchere.Bidder.Handlers.WinStatusRequestHandler;
import com.projetenchere.common.Models.Network.Communication.CurrentBids;
import com.projetenchere.common.Models.Encrypted.EncryptedOffer;
import com.projetenchere.common.Models.Network.Communication.Informations.PrivateSecurityInformations;
import com.projetenchere.common.Models.Network.Communication.Informations.PublicSecurityInformations;
import com.projetenchere.common.Models.Network.Communication.WinStatus;
import com.projetenchere.common.Models.Network.Communication.Informations.NetworkContactInformation;
import com.projetenchere.common.Handlers.InformationsRequestWithAckHandler;
import com.projetenchere.common.Handlers.RequestHandler;
import com.projetenchere.common.Models.Network.Sendable.ObjectSender;
import com.projetenchere.common.Controllers.NetworkController;
import com.projetenchere.common.Utils.EncryptionUtil;
import com.projetenchere.common.Utils.NetworkUtil;

import java.io.IOException;

public class BidderNetworkController extends NetworkController {
    private final BidderController controller;

    public BidderNetworkController(BidderController bidderController) throws Exception {
        controller = bidderController;
        myInformations = new PrivateSecurityInformations(new NetworkContactInformation(NetworkUtil.getMyIP(),24683), EncryptionUtil.generateKeyPair(),EncryptionUtil.generateKeyPair());
    }

    public PublicSecurityInformations getManagerInformations() {
        return getInformationsOf("Manager");
    }

    public PublicSecurityInformations getSellerInformations() {
        return getInformationsOf("Seller");
    }


    @Override
    public RequestHandler determineSpecificsHandler(ObjectSender objectSender) {
        if (objectSender.getObjectClass().equals(PublicSecurityInformations.class) &&
                !informationContainsPublicKey(((PublicSecurityInformations) objectSender.getObject()).getId())) {
            return new InformationsRequestWithAckHandler(this);
        }
        if (objectSender.getObjectClass().equals(WinStatus.class) &&
                controller.getParticipatedBid().contains(((WinStatus) objectSender.getObject()).getBidId())) {
            return new WinStatusRequestHandler(controller);
        }
        return null;
    }


    public void sendBidderInfosToManager() throws IOException, ClassNotFoundException {
        PublicSecurityInformations securityInformation = new PublicSecurityInformations(myInformations);
        NetworkUtil.send(
                getInformationsOf("Manager").getNetworkContactInformation().getIp(),
                getInformationsOf("Manager").getNetworkContactInformation().getPort(),
                new ObjectSender(
                        myInformations.getNetworkContactInformation().getIp(),
                        myInformations.getNetworkContactInformation().getPort(),
                        securityInformation,
                        securityInformation.getClass()
                )
        );
        ObjectSender objectSender = NetworkUtil.receive(myInformations.getNetworkContactInformation().getPort(),30000); // On garde cette façon de recevoir car le blockage est utile
        if (!objectSender.getObjectClass().equals(CurrentBids.class)) {
            throw new ClassNotFoundException("Received wrong class");
        } else {
            CurrentBids currentBids = (CurrentBids) objectSender.getObject();
            //saveInformations(currentBids.getManagerInformations()); TODO : InitContactWithManager
            controller.setCurrentBids(currentBids);
        }
    }

    public void sendOffer(EncryptedOffer encryptedOffer) throws IOException {
        NetworkUtil.send(
                getInformationsOf("Seller").getNetworkContactInformation().getIp(),
                getInformationsOf("Seller").getNetworkContactInformation().getPort(),
                new ObjectSender(
                        myInformations.getNetworkContactInformation().getIp(),
                        myInformations.getNetworkContactInformation().getPort(),
                        encryptedOffer,
                        EncryptedOffer.class
                )
        );
    }
}
