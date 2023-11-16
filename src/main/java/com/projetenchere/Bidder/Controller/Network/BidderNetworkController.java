package com.projetenchere.Bidder.Controller.Network;

import com.projetenchere.Bidder.Controller.BidderController;
import com.projetenchere.Bidder.Controller.Network.Handlers.WinStatusRequestHandler;
import com.projetenchere.common.Models.Network.Communication.CurrentBids;
import com.projetenchere.common.Models.Encrypted.EncryptedOffer;
import com.projetenchere.common.Models.Network.Communication.SecurityInformations;
import com.projetenchere.common.Models.Network.Communication.WinStatus;
import com.projetenchere.common.Models.Network.Communication.NetworkContactInformation;
import com.projetenchere.common.Models.Network.Handlers.InformationsRequestHandler;
import com.projetenchere.common.Models.Network.RequestHandler;
import com.projetenchere.common.Models.Network.Sendable.ObjectSender;
import com.projetenchere.common.Models.Network.NetworkController;
import com.projetenchere.common.Utils.Network.NetworkUtil;

import java.io.IOException;
import java.net.UnknownHostException;

public class BidderNetworkController extends NetworkController {
    private final BidderController controller;

    public BidderNetworkController(BidderController bidderController) throws UnknownHostException {
        controller = bidderController;
        saveInformations(new SecurityInformations("Manager",new NetworkContactInformation("127.0.0.1",24683)));
        //saveInformations(new SecurityInformations("Seller",new NetworkContactInformation("127.0.0.1",24682)));
        myNCI = new NetworkContactInformation(NetworkUtil.getMyIP(),24683);
    }

    public SecurityInformations getManagerInformations() {
        return getInformationsOf("Manager");
    }

    public SecurityInformations getSellerInformations() {
        return getInformationsOf("Seller");
    }


    @Override
    public RequestHandler determineSpecificsHandler(ObjectSender objectSender) {
        if (objectSender.getObjectClass().equals(SecurityInformations.class) &&
                !informationContainsPublicKey(((SecurityInformations) objectSender.getObject()).getId())) {
            return new InformationsRequestHandler(this);
        }
        if (objectSender.getObjectClass().equals(WinStatus.class) &&
                controller.getParticipatedBid().contains(((WinStatus) objectSender.getObject()).getBidId())) {
            return new WinStatusRequestHandler(controller);
        }
        return null;
    }


    public void askForInitPackage() throws IOException, ClassNotFoundException {
        NetworkUtil.send(
                getInformationsOf("Manager").getNetworkContactInformation().getIp(),
                getInformationsOf("Manager").getNetworkContactInformation().getPort(),
                new ObjectSender(
                        myNCI.getIp(),
                        myNCI.getPort(),
                        "InitPackageRequest",
                        String.class
                )
        );
        ObjectSender objectSender = NetworkUtil.receive(myNCI.getPort(),30000); // On garde cette façon de recevoir car le blockage est utile
        if (!objectSender.getObjectClass().equals(CurrentBids.class)) {
            throw new ClassNotFoundException("Received wrong class");
        } else {
            CurrentBids currentBids = (CurrentBids) objectSender.getObject();
            saveInformations(currentBids.getManagerInformations());
            controller.setCurrentBids(currentBids);
        }
    }

    public void sendOffer(EncryptedOffer encryptedOffer) throws IOException {
        NetworkUtil.send(
                getInformationsOf("Seller").getNetworkContactInformation().getIp(),
                getInformationsOf("Seller").getNetworkContactInformation().getPort(),
                new ObjectSender(
                        myNCI.getIp(),
                        myNCI.getPort(),
                        encryptedOffer,
                        EncryptedOffer.class
                )
        );
    }
}
