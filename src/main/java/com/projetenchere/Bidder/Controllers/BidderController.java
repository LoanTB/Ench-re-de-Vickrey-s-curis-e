package com.projetenchere.Bidder.Controllers;

import com.projetenchere.Bidder.Model.Bidder;
import com.projetenchere.Bidder.View.IBidderUserInterface;
import com.projetenchere.Bidder.View.commandLineInterface.BidderCommandLineInterface;
import com.projetenchere.Bidder.network.BidderClient;
import com.projetenchere.common.Controllers.Controller;
import com.projetenchere.common.Models.Bid;
import com.projetenchere.common.Models.CurrentBids;
import com.projetenchere.common.Models.Encrypted.EncryptedOffer;
import com.projetenchere.common.Models.Encrypted.SignedEncryptedOfferSet;
import com.projetenchere.common.Models.Encrypted.SignedPublicKey;
import com.projetenchere.common.Models.WinStatus;
import com.projetenchere.common.Models.Offer;
import com.projetenchere.exception.BidAbortedException;

import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BidderController extends Controller {
    private final IBidderUserInterface ui = new BidderCommandLineInterface();
    BidderClient client = new BidderClient();
    private CurrentBids currentBids;
    private final List<String> participatedBid = new ArrayList<>();
    private final Map<String, WinStatus> results = new HashMap<>();
    private final Bidder bidder = new Bidder();
    private PublicKey managerPubKey;

    public void setSignatureConfig() throws Exception {
        setSignatureConfig(ui,bidder);
    }

    public void setCurrentBids(CurrentBids currentBids) {
        this.currentBids = currentBids;
    }

    public void askForManagerPubKey(){
        ui.tellWaitManagerSecurityInformations();
        managerPubKey = client.getManagerPubKey();
        System.out.println("Public key received");
    }

    public void askForCurrentBids() {
        currentBids = client.getCurrentBids();
        System.out.println("Received bids");
    }

    public void initWithManager() {
        client.connectToManager();
        askForCurrentBids();
        askForManagerPubKey();
    }

    public void showBids() {
        System.out.println(this.currentBids.getCurrentBids().size());
        ui.displayBid(this.currentBids);
    }


    public void readAndSendOffer() throws BidAbortedException, SignatureException {
        Offer offer = ui.readOffer(bidder, currentBids);
        Bid bid = currentBids.getBid(offer.getIdBid());
        if (bid == null) throw new RuntimeException("");
        EncryptedOffer encryptedOffer;
        try {
            encryptedOffer = new EncryptedOffer(bidder.getSignature(), offer, bidder.getKey(), managerPubKey, bid.getId());
        } catch (GeneralSecurityException e) {
            throw new RuntimeException("Could not encrypt offer");
        }
        participatedBid.add(offer.getIdBid());
        client.connectToSeller(bid.getSellerSocketAddress());
        ui.tellOfferSent();
        SignedEncryptedOfferSet set = client.sendOfferReceiveList(encryptedOffer);
        //TODO: check set
        if(/*set does not contain offer*/ false) {
            client.stopEverything();
            throw new BidAbortedException("Offer was not present is set");
        }
        client.stopManager();
        SignedPublicKey key = new SignedPublicKey(bidder.getKey(), bidder.getSignature());
        WinStatus status = client.validateAndGetWinStatus(key);
        this.results.put(bid.getId(), status);
        if (status.isWinner()) {
            System.out.println("Prix à payer : " + status.getPrice());
        } else {
            System.out.println("Vous avez perdu");
        }
        client.stopSeller();
    }

    public void displayHello(){
        ui.displayHello();
    }
}
