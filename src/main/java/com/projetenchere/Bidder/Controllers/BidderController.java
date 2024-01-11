package com.projetenchere.Bidder.Controllers;

import com.projetenchere.Bidder.Model.Bidder;
import com.projetenchere.Bidder.View.IBidderUserInterface;
import com.projetenchere.Bidder.View.graphicalUserInterface.IBidderUserInterfaceFactory;
import com.projetenchere.Bidder.network.BidderClient;
import com.projetenchere.common.Controllers.Controller;
import com.projetenchere.common.Models.Bid;
import com.projetenchere.common.Models.CurrentBids;
import com.projetenchere.common.Models.Encrypted.EncryptedOffer;
import com.projetenchere.common.Models.WinStatus;
import com.projetenchere.common.Models.Offer;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BidderController extends Controller {
    private IBidderUserInterface ui;

    public BidderController(IBidderUserInterfaceFactory uiFactory) throws Exception {
        this.ui = uiFactory.createBidderUserInterface();
    }
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
    }

    public void askForCurrentBids() {
        currentBids = client.getCurrentBids();
        ui.tellReceiptOfCurrentBids();
    }

    public void initWithManager() {
        client.connectToManager();
        askForCurrentBids();
        askForManagerPubKey();
        client.stopManager();
    }

    public void showBids() {
        System.out.println(this.currentBids.getCurrentBids().size());
        ui.displayBid(this.currentBids);
    }

    public void readAndSendOffer() throws Exception {
        Offer offer = ui.readOffer(bidder, currentBids);
        Bid bid = currentBids.getBid(offer.getIdBid());

        EncryptedOffer encryptedOffer = new EncryptedOffer(bidder.getSignature(), offer, bidder.getKey(), managerPubKey, bid.getId());

        participatedBid.add(offer.getIdBid());
        client.connectToSeller(bid.getSellerSocketAddress());
        ui.tellOfferSent();
        WinStatus status = client.sendOfferAndWaitForResult(encryptedOffer);
        this.results.put(bid.getId(), status);
        if (status.isWinner()) {
            ui.tellOfferWon(status.getPrice());
        } else {
            ui.tellOfferLost();
        }
        client.stopSeller();
    }

    public List<String> getParticipatedBid(){
        return participatedBid;
    }

    public void displayHello(){ui.displayHello();}

    public IBidderUserInterface getUi() {
        return ui;
    }
}
