package com.projetenchere.Bidder.Controllers;

import com.projetenchere.Bidder.Model.Bidder;
import com.projetenchere.Bidder.View.graphicalUserInterface.BidderGraphicalUserInterface;
import com.projetenchere.Bidder.network.BidderClient;
import com.projetenchere.common.Controllers.Controller;
import com.projetenchere.common.Models.*;
import com.projetenchere.common.Models.SignedPack.*;
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

    private final Map<String, SigPack_Results> results = new HashMap<>();

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


    public void verifyIfEjectOrUnknownPlayerStatus(PlayerStatus status) throws BidAbortedException {
        if(status.isEjected()){
            client.stopSeller();
            client.stopManager();
            throw new BidAbortedException("Bidder's offer was not present in set of offers.");
        }
        if(status.isUnknown()){
            client.stopSeller();
            client.stopManager();
            throw new BidAbortedException("Bidder's key was not present in bidders keys initialization.");
        }
    }



    public void readAndSendOffer() throws BidAbortedException, SignatureException {
        Offer offer = ui.readOffer(bidder, currentBids);
        Bid bid = currentBids.getBid(offer.getIdBid());
        if (bid == null) throw new RuntimeException("");

        client.stopManager();

        client.connectToSeller(bid.getSellerSocketAddress());


        int participation = 1;
        byte[] participationSigned = SignatureUtil.signData(participation,bidder.getSignature());

        SigPack_Confirm participationPack = new SigPack_Confirm(participation,participationSigned,bidder.getKey(),bid.getId());

        SigPack_Confirm participationConfirmation = client.sendParticipationReceiveConfirm(participationPack);

        if(!SignatureUtil.verifyDataSignature(SignatureUtil.objectToArrayByte(participationConfirmation.getObject()),participationConfirmation.getObjectSigned(),participationConfirmation.getSignaturePubKey()))
        {
            client.stopSeller();
            throw new SignatureException("Seller's signature has been compromised.");
        }

        int participationState = (int) participationConfirmation.getObject();
        if(participationState == 0){
            ui.addLogMessage("Confirmation de participation rejetée.");
            client.stopSeller();
            throw new BidAbortedException("Participation rejected.");
        }else {
            ui.addLogMessage("Confirmation de participation reçue.");
        }


        int nbParticipant = (int) participationConfirmation.getObject();
        SigPack_EncOffer sigPackEncOffer;
        try {
            //TODO Utiliser le nb de participant pour encoder le prix en base nbParticipant

            byte[] price = EncryptionUtil.encryptPrice(offer.getPrice(), managerPubKey);
            byte[] priceSigned = SignatureUtil.signData(price, bidder.getSignature());
            sigPackEncOffer = new SigPack_EncOffer(price,priceSigned,bidder.getKey(),bid.getId());
        } catch (GeneralSecurityException e) {
            throw new RuntimeException("Could not encrypt offer");
        }
        participatedBid.add(offer.getIdBid());


        SigPack_EncOffersProduct set = client.sendOfferReceiveList(sigPackEncOffer);
        ui.tellOfferSent();

        if(!SignatureUtil.verifyDataSignature(SignatureUtil.objectToArrayByte(set.getObject()),set.getObjectSigned(),set.getSignaturePubKey()))
        {
            client.stopSeller();
            throw new SignatureException("Seller's signature has been compromised.");
        }


//Faire remarquer l'absence du chiffré.
        int msg;
        if (!set.getSetOffers().contains(sigPackEncOffer)) {
            msg = 0;
        }else{
            msg = 1;
        }

        byte[] msgSigned = SignatureUtil.signData(msg, bidder.getSignature());
        SigPack_Confirm key = new SigPack_Confirm(msg,msgSigned,bidder.getKey(), sigPackEncOffer.getBidId());

        //TODO S2 : Ajouter le fait que le vendeur signe le EndPack.
        EndPack pack = client.validateAndGetResults(key);

        if(!pack.isResultsInside()){
            this.verifyIfEjectOrUnknownPlayerStatus(pack.getStatus());
        }

        SigPack_Results sellerResults = pack.getResults();

        SigPack_PriceWin autorityResults = (SigPack_PriceWin) sellerResults.getObject();

        //TODO S2 : Refactor le code avec des nouvelles méthodes pour couvrir les situations où une clé est falsifiée et éviter la duplication de code.

        if(!SignatureUtil.verifyDataSignature(SignatureUtil.objectToArrayByte(((SigPack_PriceWin)sellerResults.getObject()).getObject()),sellerResults.getObjectSigned(),sellerResults.getSignaturePubKey()))
        {
            client.stopSeller();
            throw new BidAbortedException("Results compromised : Seller's key falsified.");
        }
        if(!SignatureUtil.verifyDataSignature( SignatureUtil.objectToArrayByte(autorityResults.getObject()),autorityResults.getObjectSigned(),autorityResults.getSignaturePubKey()))
        {
            client.stopSeller();
            throw new BidAbortedException("Results compromised : Manager's key falsified.");
        }

        double priceWin = (double) autorityResults.getObject();


        this.results.put(bid.getId(),sellerResults);

        //TODO S2 : Refactor le code. + Vérifier la signature du PlayerStatus.

        if (offer.getPrice() == priceWin) {

            int exp = 1;
            byte[] expByte = SignatureUtil.objectToArrayByte(exp);
            byte[] expSigned = SignatureUtil.signData(expByte,this.bidder.getSignature());
            SigPack_Confirm expression = new SigPack_Confirm(exp,expSigned, this.bidder.getKey(),bid.getId());

            PlayerStatus status = client.sendExpressWin(expression);

            verifyIfEjectOrUnknownPlayerStatus(status);
            if(status.isWinner()){
                ui.tellOfferWon(offer.getPrice());
            }
            else {
                ui.tellOfferLost();
            }
        } else {
            ui.tellOfferLost();
        }

        client.stopSeller();
    }

    public void setSignatureConfig() throws Exception {
        setSignatureConfig(ui, bidder,"bidder");
    }

    public void displayHello() {
        ui.displayHello();
    }



}
