package com.projetenchere.Bidder.Controllers;

import com.projetenchere.Bidder.Model.Bidder;
import com.projetenchere.Bidder.View.graphicalUserInterface.BidderGraphicalUserInterface;
import com.projetenchere.Bidder.network.BidderClient;
import com.projetenchere.common.Controllers.Controller;
import com.projetenchere.common.Models.Bid;
import com.projetenchere.common.Models.CurrentBids;
import com.projetenchere.common.Models.Encrypted.EncryptedOffer;
import com.projetenchere.common.Models.Encrypted.SignedEncryptedOfferSet;
import com.projetenchere.common.Models.Encrypted.SigPack_PublicKey;
import com.projetenchere.common.Models.Offer;
import com.projetenchere.common.Models.WinStatus;
import com.projetenchere.common.Utils.SignatureUtil;
import com.projetenchere.exception.BidAbortedException;

import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BidderController extends Controller {
    private final List<String> participatedBid = new ArrayList<>();
    private final Map<String, WinStatus> results = new HashMap<>();
    private final Bidder bidder = new Bidder();
    BidderGraphicalUserInterface ui;
    BidderClient client = new BidderClient();
    private CurrentBids currentBids;
    private PublicKey managerPubKey;

    public BidderController(BidderGraphicalUserInterface ui) {
        this.ui = ui;
    }

    public void askForManagerPubKey() {
        ui.tellWaitManagerSecurityInformations();
        managerPubKey = client.getManagerPubKey();
    }

    public void askForCurrentBids() {
        currentBids = client.getCurrentBids();
        ui.tellReceiptOfCurrentBids();
        ui.displayBid(this.currentBids);
    }

    public void initWithManager() {
        client.connectToManager();
        askForCurrentBids();
        askForManagerPubKey();
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
        if (!set.getSet().contains(encryptedOffer)) {
            client.stopEverything();
            throw new BidAbortedException("Offer was not present is set");
        }
        client.stopManager();

        String msg = "ok";
        byte[] msgSigned = SignatureUtil.signData(msg.getBytes(), bidder.getSignature());
        SigPack_PublicKey key = new SigPack_PublicKey(msg,msgSigned,bidder.getKey());
        WinStatus status = client.validateAndGetWinStatus(key);

        this.results.put(bid.getId(), status);
        if (status.isWinner()) {
            ui.tellOfferWon(status.getPrice());
        } else {
            ui.tellOfferLost();
        }
        client.stopSeller();
    }

    public void setSignatureConfig() throws Exception {
        setSignatureConfig(ui, bidder);
    }

    public void displayHello() {
        ui.displayHello();
    }

}
