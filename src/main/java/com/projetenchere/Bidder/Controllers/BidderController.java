package com.projetenchere.Bidder.Controllers;

import com.projetenchere.Bidder.Model.Bidder;
import com.projetenchere.Bidder.View.IBidderUserInterface;
import com.projetenchere.Bidder.View.commandLineInterface.BidderCommandLineInterface;
import com.projetenchere.common.Controllers.Controller;
import com.projetenchere.common.Models.Identity;
import com.projetenchere.common.Models.Network.Communication.CurrentBids;
import com.projetenchere.common.Models.Encrypted.EncryptedOffer;
import com.projetenchere.common.Models.Network.Communication.CurrentBidsPublicKeys;
import com.projetenchere.common.Models.Network.Communication.Informations.PublicSecurityInformations;
import com.projetenchere.common.Models.Network.Communication.WinStatus;
import com.projetenchere.common.Models.Offer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.lang.Thread.sleep;

public class BidderController extends Controller {
    private final IBidderUserInterface ui = new BidderCommandLineInterface();
    private final BidderNetworkController networkController = new BidderNetworkController(this);
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

    public void networkListeningInitialization() {
        Thread thread = new Thread(networkController);
        thread.start();
    }

    public void addResult(WinStatus result){
        results.add(result);
    }

    public List<String> getParticipatedBid(){
        return participatedBid;
    }

    public void readInfos() throws Exception {
        bidder.setIdentity(new Identity(UUID.randomUUID().toString(),ui.readName(),ui.readSurname(),"Bidder"));
        networkController.setMyInformationsWithPort(bidder.getIdentity(),ui.readPort());
        bidder.setInformations(networkController.getMyPublicInformations());
    }

    public void waitToReceiveBids() {
        ui.tellWaitBidsAnnoncement();
        while (currentBids == null) {
            try {
                wait(1000); // Eviter une utilisation excessive du CPU
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void waitToReceiveBidsPublicKeys() {
        ui.tellWaitBidsPublicKeysAnnoncement();
        while (currentBidsPublicKeys == null) {
            try {
                wait(1000); // Eviter une utilisation excessive du CPU
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void showBid() {
        waitToReceiveBids();
        ui.displayBid(this.currentBids);
    }

    public void readAndSendOffer() throws Exception {
        waitManagerContactKeys(); // S'assurer que le contact est bien établie avec le manager
        Offer offer = ui.readOffer(bidder);
        waitToReceiveBidsPublicKeys();
        EncryptedOffer encryptedOffer = new EncryptedOffer(offer, currentBidsPublicKeys.getPublicKeyOfBid(offer.getIdBid()));
        networkController.sendTo(currentBids.getBid(offer.getIdBid()).getSeller().getIdentity().getId(),encryptedOffer);
        participatedBid.add(offer.getIdBid());
        ui.tellOfferSent();
    }

    public void waitForPrice() {
        ui.tellWaitOfferResult();
        while (results.isEmpty()) {
            try {
                wait(1000); // Eviter une utilisation excessive du CPU
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
            try {
                wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendBidderInfosToManager() throws Exception {
        networkListeningInitialization();
        boolean succes;
        try {
            networkController.sendMySI("Manager");
            succes = true;
        } catch (Exception ignore){
            succes = false;
            ui.tellWaitManager();
        }
        while (!succes) {
            sleep(1000);
            try {
                networkController.sendMySI("Manager");
                succes = true;
                ui.tellManagerFound();
            } catch (Exception ignore){
                succes = false;
            }
        }
    }

    public void displayHello(){ui.displayHello();}

    public IBidderUserInterface getUi() {
        return ui;
    }
}
