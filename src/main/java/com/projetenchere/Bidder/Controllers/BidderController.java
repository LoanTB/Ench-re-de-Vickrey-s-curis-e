package com.projetenchere.Bidder.Controllers;

import com.projetenchere.Bidder.Model.Bidder;
import com.projetenchere.Bidder.View.IBidderUserInterface;
import com.projetenchere.Bidder.View.commandLineInterface.BidderCommandLineInterface;
import com.projetenchere.Bidder.network.BidderClient;
import com.projetenchere.common.Controllers.Controller;
import com.projetenchere.common.Models.Network.Communication.CurrentBids;
import com.projetenchere.common.Models.Encrypted.EncryptedOffer;
import com.projetenchere.common.Models.Network.Communication.WinStatus;
import com.projetenchere.common.Models.Offer;

import java.security.PublicKey;
import java.security.Signature;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class BidderController extends Controller {
    private final IBidderUserInterface ui = new BidderCommandLineInterface();
    BidderClient client = new BidderClient();
    private CurrentBids currentBids;
    private final List<String> participatedBid = new ArrayList<>();
    private final List<WinStatus> results = new ArrayList<>();
    private final Bidder bidder = new Bidder();
    private PublicKey managerPubKey;

    public void setCurrentBids(CurrentBids currentBids) {
        this.currentBids = currentBids;
    }

    public void askForManagerPubKey(){
        ui.tellWaitManagerSecurityInformations();
        managerPubKey = client.getManagerPubKey();
    }

    public void askForCurrentBids() {
        currentBids = client.getCurrentBids();
    }

    public void initWithManager() {
        client.connectToManager();
        askForManagerPubKey();
        askForCurrentBids();
    }

    public void showBids() {
        ui.displayBid(this.currentBids);
    }

    public void readAndSendOffer() throws Exception {
        Offer offer = ui.readOffer(bidder, currentBids);
        EncryptedOffer encryptedOffer = new EncryptedOffer(bidder.getSignature(), offer, managerPubKey, bidder.getKey());
        participatedBid.add(offer.getIdBid());
        // TODO: connect to seller
        // client.connectToSeller();
        ui.tellOfferSent();

    }

    public void addResult(WinStatus result){
        results.add(result);
    }

    public List<String> getParticipatedBid(){
        return participatedBid;
    }

    public void displayHello(){ui.displayHello();}

    public IBidderUserInterface getUi() {
        return ui;
    }
}
