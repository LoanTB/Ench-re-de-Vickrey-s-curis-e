package com.projetenchere.Bidder.Controllers;

import com.projetenchere.Bidder.Model.Bidder;
import com.projetenchere.Bidder.View.graphicalUserInterface.BidderGraphicalUserInterface;
import com.projetenchere.Bidder.network.BidderClient;
import com.projetenchere.common.Controllers.Controller;
import com.projetenchere.common.Models.Bid;
import com.projetenchere.common.Models.CurrentBids;
import com.projetenchere.common.Models.PlayerStatus.PlayerStatus;
import com.projetenchere.common.Models.SignedPack.SigPack_EncOffer;
import com.projetenchere.common.Models.SignedPack.SigPack_EncOffersProduct;
import com.projetenchere.common.Models.SignedPack.SigPack_PublicKey;
import com.projetenchere.common.Models.Offer;
import com.projetenchere.common.Utils.EncryptionUtil;
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
    private final Map<String, PlayerStatus> results = new HashMap<>();
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
        SigPack_EncOffer sigPackEncOffer;
        try {
            byte[] price = EncryptionUtil.encryptPrice(offer.getPrice(), managerPubKey);
            byte[] priceSigned = SignatureUtil.signData(price, bidder.getSignature());
            sigPackEncOffer = new SigPack_EncOffer(price,priceSigned,bidder.getKey(),bid.getId());
        } catch (GeneralSecurityException e) {
            throw new RuntimeException("Could not encrypt offer");
        }
        participatedBid.add(offer.getIdBid());
        client.connectToSeller(bid.getSellerSocketAddress());
        ui.tellOfferSent();
        SigPack_EncOffersProduct set = client.sendOfferReceiveList(sigPackEncOffer);

        //On vérifier la signature du vendeur
        if(!SignatureUtil.verifyDataSignature(SignatureUtil.objectToArrayByte(set.getObject()),set.getObjectSigned(),set.getSignaturePubKey()))
        {
            client.stopEverything();
            throw new SignatureException("Seller's signature has been compromised.");
        }

//Faire remarquer l'absence du chiffré.
        int msg;
        if (!set.getSetOffers().contains(sigPackEncOffer)) {
            msg = 0;
        }else{
            msg = 1;
        }
        client.stopManager();

        byte[] msgSigned = SignatureUtil.signData(msg, bidder.getSignature());
        SigPack_PublicKey key = new SigPack_PublicKey(msg,msgSigned,bidder.getKey());

        PlayerStatus status = client.validateAndGetWinStatus(key);

        if(status.isEjected()){
            client.stopEverything();
            throw new BidAbortedException("Bidder's offer was not present in set of offers.");
        }
        if(status.isUnknown()){
            client.stopEverything();
            throw new BidAbortedException("Bidder's key was not present in bidders keys initialization.");
        }

        //TODO : Changer le comportement.
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
