package com.projetenchere.Bidder.Controllers;

import com.projetenchere.Bidder.Handlers.CurrentBidsHandler;
import com.projetenchere.Bidder.Handlers.CurrentBidsPublicKeysHandler;
import com.projetenchere.Bidder.Handlers.PublicSecurityInformationHandler;
import com.projetenchere.Bidder.Handlers.WinStatusRequestHandler;
import com.projetenchere.common.Models.Network.Communication.CurrentBids;
import com.projetenchere.common.Models.Encrypted.EncryptedOffer;
import com.projetenchere.common.Models.Network.Communication.Informations.PrivateSecurityInformations;
import com.projetenchere.common.Models.Network.Communication.Informations.PublicSecurityInformations;
import com.projetenchere.common.Models.Network.Communication.ObjectReceived;
import com.projetenchere.common.Models.Network.Communication.WinStatus;
import com.projetenchere.common.Models.Network.Communication.Informations.NetworkContactInformation;
import com.projetenchere.common.Handlers.InformationsRequestWithAckHandler;
import com.projetenchere.common.Handlers.RequestHandler;
import com.projetenchere.common.Models.Network.Sendable.ObjectSender;
import com.projetenchere.common.Controllers.NetworkController;
import com.projetenchere.common.Utils.EncryptionUtil;
import com.projetenchere.common.Utils.NetworkUtil;

import java.io.IOException;
import java.util.UUID;

public class BidderNetworkController extends NetworkController {
    private final BidderController controller;

    public BidderNetworkController(BidderController bidderController) throws Exception {
        controller = bidderController;
        myInformations = new PrivateSecurityInformations(UUID.randomUUID().toString(), new NetworkContactInformation(NetworkUtil.getMyIP(),24683), EncryptionUtil.generateKeyPair(),EncryptionUtil.generateKeyPair());
    }

    public PublicSecurityInformations getManagerInformations() {
        return getInformationsOf("Manager");
    }

    public PublicSecurityInformations getSellerInformations() {
        return getInformationsOf("Seller");
    }


    @Override
    public RequestHandler determineSpecificsHandler(ObjectReceived objectReceived) {
        if (objectReceived.getObjectSended().getObjectClass().equals(PublicSecurityInformations.class) &&
                !informationContainsPublicKeys(((PublicSecurityInformations) objectReceived.getObjectSended().getObject()).getId())) {
            return new InformationsRequestWithAckHandler(this);
        }
        if (objectReceived.getObjectSended().getObjectClass().equals(WinStatus.class) &&
                controller.getParticipatedBid().contains(((WinStatus) objectReceived.getObjectSended().getObject()).getBidId())) {
            return new WinStatusRequestHandler(controller);
        }
        if (objectReceived.getObjectSended().getObjectClass().equals(CurrentBids.class)) {
            return new CurrentBidsHandler(controller);
        }
        if (objectReceived.getObjectSended().getObjectClass().equals(PublicSecurityInformations.class)) {
            return new PublicSecurityInformationHandler(this);
        }
        if (objectReceived.getObjectSended().getObjectClass().equals(PublicSecurityInformations.class)) {
            return new CurrentBidsPublicKeysHandler(controller);
        }
        return null;
    }


    public void sendBidderInfosToManager() throws IOException, ClassNotFoundException {
        PublicSecurityInformations securityInformation = new PublicSecurityInformations(myInformations);
        NetworkUtil.send(
                getInformationsOf("Manager").getNetworkContactInformation().ip(),
                getInformationsOf("Manager").getNetworkContactInformation().port(),
                new ObjectSender(
                        myInformations.getNetworkContactInformation().ip(),
                        myInformations.getNetworkContactInformation().port(),
                        securityInformation,
                        securityInformation.getClass()
                )
        );
        ObjectSender objectSender = NetworkUtil.receive(myInformations.getNetworkContactInformation().port(),30000); // On garde cette façon de recevoir car le blockage est utile
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
                getInformationsOf("Seller").getNetworkContactInformation().ip(),
                getInformationsOf("Seller").getNetworkContactInformation().port(),
                new ObjectSender(
                        myInformations.getNetworkContactInformation().ip(),
                        myInformations.getNetworkContactInformation().port(),
                        encryptedOffer,
                        EncryptedOffer.class
                )
        );
    }
}
