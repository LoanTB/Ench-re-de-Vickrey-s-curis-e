package com.projetenchere.Bidder.Controllers;

import com.projetenchere.Bidder.Model.Bidder;
import com.projetenchere.Bidder.View.IBidderUserInterface;
import com.projetenchere.Bidder.View.commandLineInterface.BidderCommandLineInterface;
import com.projetenchere.common.Controllers.Controller;
import com.projetenchere.common.Models.Identity;
import com.projetenchere.common.Models.Network.Communication.CurrentBids;
import com.projetenchere.common.Models.Encrypted.EncryptedOffer;
import com.projetenchere.common.Models.Network.Communication.CurrentBidsPublicKeys;
import com.projetenchere.common.Models.Network.Communication.WinStatus;
import com.projetenchere.common.Models.Offer;
import com.projetenchere.common.Party;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.lang.Thread.sleep;

public class BidderController extends Controller {
    private final IBidderUserInterface ui = new BidderCommandLineInterface();
    private CurrentBids currentBids = null;
    private CurrentBidsPublicKeys currentBidsPublicKeys = null;
    private final List<String> participatedBid = new ArrayList<>();
    private final List<WinStatus> results = new ArrayList<>();
    private final Bidder bidder = new Bidder();

    public void setCurrentBids(CurrentBids currentBids) {
        this.currentBids = currentBids;
    }

    public void setCurrentBidsPublicKeys(CurrentBidsPublicKeys currentBidsPublicKeys) {
        this.currentBidsPublicKeys = currentBidsPublicKeys;
    }


    public void addResult(WinStatus result){
        results.add(result);
    }

    public List<String> getParticipatedBid(){
        return participatedBid;
    }

    public void readInfos() throws Exception {
        bidder.setIdentity(new Identity(UUID.randomUUID().toString(),ui.readName(),ui.readSurname(),"Bidder"));
    }

    public void waitToReceiveBids() {
        ui.tellWaitBidsAnnoncement();
        while (currentBids == null) {
            waitSychro(1000);
        }
    }

    public void waitToReceiveBidsPublicKeys() {
        ui.tellWaitBidsPublicKeysAnnoncement();
        while (currentBidsPublicKeys == null) {
            waitSychro(1000);
        }
    }

    public void showBid() {
        waitToReceiveBids();
        ui.displayBid(this.currentBids);
    }

    public void readAndSendOffer() throws Exception {
        Offer offer = ui.readOffer(bidder, currentBids);
        waitToReceiveBidsPublicKeys();
        EncryptedOffer encryptedOffer = new EncryptedOffer(offer, currentBidsPublicKeys.getKeyOfBid(offer.getIdBid()));
        networkController.sendTo(currentBids.getBid(offer.getIdBid()).getSeller().getIdentity().getId(),encryptedOffer);
        participatedBid.add(offer.getIdBid());
        ui.tellOfferSent();
    }

    public void waitForPrice() {
        ui.tellWaitOfferResult();
        while (results.isEmpty()) {
            waitSychro(1000);
        }
        if (results.get(0).isWinner()){
            ui.tellOfferWon(results.get(0).getPrice());
        } else {
            ui.tellOfferLost();
        }
    }

    public void waitManagerContactKeys(){
        if (networkController.informationContainsPublicKeys("Manager")){
            return;
        }
        ui.tellWaitManagerSecurityInformations();
        while (!networkController.informationContainsPublicKeys("Manager")) {
            waitSychro(1000);
        }
    }

    public void sendBidderInfosToManager() throws Exception {
        Party manager =
    }

    public void displayHello(){ui.displayHello();}

    public IBidderUserInterface getUi() {
        return ui;
    }

    public void sendRequestOffers() throws Exception {
        waitManagerContactKeys();
        ui.tellSendRequestOffers();
        networkController.sendTo("Manager","InitPackageRequest");
    }
}
