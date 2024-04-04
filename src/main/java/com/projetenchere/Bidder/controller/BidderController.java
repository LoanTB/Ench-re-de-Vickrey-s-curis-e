package com.projetenchere.bidder.controller;

import com.projetenchere.bidder.model.Bidder;
import com.projetenchere.bidder.view.IBidderUserInterface;
import com.projetenchere.bidder.network.BidderClient;
import com.projetenchere.common.controller.Controller;
import com.projetenchere.common.model.*;
import com.projetenchere.common.model.signedPack.*;
import com.projetenchere.common.util.EncryptionUtil;
import com.projetenchere.common.util.SignatureUtil;
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
    IBidderUserInterface ui;
    BidderClient client = new BidderClient();
    private CurrentBids currentBids;
    private PublicKey managerPubKey;

    public BidderController(IBidderUserInterface ui) {
        this.ui = ui;
    }

    public void askForManagerPubKey() throws SignatureException {
        ui.tellWaitManagerSecurityInformations();
        SigPack_PubKey pubSigned = client.getManagerPubKey();
        if(!SignatureUtil.verifyDataSignature(SignatureUtil.objectToArrayByte(pubSigned.getObject()),pubSigned.getObjectSigned(),pubSigned.getSignaturePubKey())){
            ui.tellFalsifiedSignatureManager();
            throw new SignatureException("manager's signature falsified.");
            //TODO : Trouver une meilleure fin d'enchères compromises pour les cas où la signature est usurpée.
        }
        managerPubKey = (PublicKey) pubSigned.getObject();
    }

    public void askForCurrentBids() throws SignatureException {
        SigPack_CurrentBids currBids =  client.getCurrentBids();

        if(!SignatureUtil.verifyDataSignature(SignatureUtil.objectToArrayByte(currBids.getObject()),currBids.getObjectSigned(),currBids.getSignaturePubKey()))
        {
            ui.tellFalsifiedSignatureManager();
            throw new SignatureException("manager's signature falsified.");
            //TODO : Trouver une meilleure fin d'enchères compromises pour les cas où la signature est usurpée.
        }

        currentBids = (CurrentBids) currBids.getObject();
        ui.tellReceiptOfCurrentBids();
        ui.displayBid(this.currentBids);
    }



    public void initWithManager() throws SignatureException {
        client.connectToManager();
        askForCurrentBids();
        askForManagerPubKey();
    }


    public void verifyIfEjectOrUnknownPlayerStatus(PlayerStatus status) throws BidAbortedException {
        if(status.isEjected()){
            client.stopSeller();
            client.stopManager();
            throw new BidAbortedException("bidder's offer was not present in set of offers.");
        }
        if(status.isUnknown()){
            client.stopSeller();
            client.stopManager();
            throw new BidAbortedException("bidder's key was not present in bidders keys initialization.");
        }
    }



    public synchronized void readAndSendOffer() throws BidAbortedException, SignatureException {
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
            ui.tellFalsifiedSignatureSeller();
            throw new SignatureException("seller's signature has been compromised.");
            //TODO : Trouver une meilleure fin d'enchères compromises pour les cas où la signature est usurpée.
        }

        int participationState = (int) participationConfirmation.getObject();
        if(participationState == 0){
            //ui.addLogMessage("Confirmation de participation rejetée."); //TODO : Créer une méthode.
            client.stopSeller();
            throw new BidAbortedException("Participation rejected.");
        }else {
            //ui.addLogMessage("Confirmation de participation reçue.");//TODO : Créer une méthode
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
            ui.tellFalsifiedSignatureSeller();
            throw new SignatureException("seller's signature has been compromised.");
            //TODO : Trouver une meilleure fin d'enchères compromises pour les cas où la signature est usurpée.
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


        EndPack pack = client.validateAndGetResults(key); //TODO FIX : Bloque ici.

        if(!pack.isResultsInside()){
            this.verifyIfEjectOrUnknownPlayerStatus(pack.getStatus());
        }

        SigPack_Results sellerResults = pack.getResults();

        SigPack_PriceWin autorityResults = (SigPack_PriceWin) sellerResults.getObject();

        if(!SignatureUtil.verifyDataSignature(SignatureUtil.objectToArrayByte(((SigPack_PriceWin)sellerResults.getObject()).getObject()),sellerResults.getObjectSigned(),sellerResults.getSignaturePubKey()))
        {
            client.stopSeller();
            ui.tellFalsifiedSignatureSeller();
            throw new BidAbortedException("Results compromised : seller's signature falsified.");
            //TODO : Trouver une meilleure fin d'enchères compromises pour les cas où la signature est usurpée.
        }
        if(!SignatureUtil.verifyDataSignature( SignatureUtil.objectToArrayByte(autorityResults.getObject()),autorityResults.getObjectSigned(),autorityResults.getSignaturePubKey()))
        {
            client.stopSeller();
            ui.tellFalsifiedSignatureManager();
            throw new BidAbortedException("Results compromised : manager's signature falsified.");
            //TODO : Trouver une meilleure fin d'enchères compromises pour les cas où la signature est usurpée.
        }

        double priceWin = (double) autorityResults.getObject();

        this.results.put(bid.getId(),sellerResults);


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
