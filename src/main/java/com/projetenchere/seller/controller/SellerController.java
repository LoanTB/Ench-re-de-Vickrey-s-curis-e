package com.projetenchere.seller.controller;

import com.projetenchere.common.controller.Controller;
import com.projetenchere.common.model.PlayerStatus;
import com.projetenchere.common.model.signedPack.*;
import com.projetenchere.common.network.Headers;
import com.projetenchere.common.network.Server;
import com.projetenchere.common.util.NetworkUtil;
import com.projetenchere.common.util.SignatureUtil;
import com.projetenchere.seller.model.Seller;
import com.projetenchere.seller.network.SellerClient;
import com.projetenchere.seller.network.handler.ChecklistOkReplyer;
import com.projetenchere.seller.network.handler.EncryptedOfferReplyer;
import com.projetenchere.seller.network.handler.ParticipationReplyer;
import com.projetenchere.seller.network.handler.WinnerReplyer;
import com.projetenchere.seller.view.ISellerUserInterface;

import java.net.InetSocketAddress;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.util.*;

public class SellerController extends Controller {
    private final SellerClient client = new SellerClient();
    private final Server server = new Server(24682);
    private final Seller seller = Seller.getInstance();
    ISellerUserInterface ui;

    public SellerController(ISellerUserInterface ui) {
        this.ui = ui;
    }

    public void setSignatureConfig() throws Exception {
        setSignatureConfig(ui, seller,"seller");
    }

    public void createMyBid() {
        seller.setMyBid(ui.askBid());
        seller.getMyBid().setSellerInformations(new InetSocketAddress(NetworkUtil.getMyIP(), NetworkUtil.SELLER_PORT));
        seller.getMyBid().setPubKeySignatureSeller(seller.getKey());
        seller.setEncryptedOffers(new Set_SigPackEncOffer(seller.getMyBid().getId(), new HashSet<>(),0));
        ui.displayBidCreated(seller.getMyBid());
    }

    public void sendMyBid() throws SignatureException {
        client.connectToManager();
        if (seller.getMyBid() == null) throw new RuntimeException("No bid was registered");
        ui.tellSendBidToManager();

        byte[] bidSigned = SignatureUtil.signData(SignatureUtil.objectToArrayByte(seller.getMyBid()),seller.getSignature());
        SigPack_Bid sigBid = new SigPack_Bid(seller.getMyBid(),bidSigned,seller.getKey());
        client.sendBid(sigBid);
    }

    public boolean auctionInProgress() {
        return (!this.seller.getMyBid().isOver());
    }

    public synchronized boolean waitAllOffers(){
        return seller.getBidderParticipant().size() != seller.getEncryptedOffersSet().getOffers().size();
    }

    public void receiveOffers() {
        server.start();
        server.addHandler(Headers.GET_PARTICIPATION, new ParticipationReplyer());
        ui.tellWaitForParticipation();
        while (auctionInProgress()) {
            waitSynchro(1000);
        }

        server.removeHandler(Headers.GET_PARTICIPATION);
        server.addHandler(Headers.SEND_OFFER, new EncryptedOfferReplyer());
        server.addHandler(Headers.GET_RESULTS, new ChecklistOkReplyer());

        while (waitAllOffers()) {
            waitSynchro(1000); //TODO : Trouver une solution pour attendre proprement
        }

//        System.out.println("eauinretsaueiu");

        ui.tellEndOfParticipation();
        ui.tellSendBiddersVerification();

        //server.removeHandler(Headers.SEND_OFFER);
        Map<PublicKey, byte[]> map = seller.getBidders();
//        System.out.println("bjoud");
    }

    public void askManagerToResolveBid() throws GeneralSecurityException {

        SigPack_EncOffersProduct offers = seller.getOffersProductSignedBySeller();

        ui.tellSendResolutionToManager();

//        System.out.println("nld,tiuednr,uiejlc,.uievdr");
        SigPack_PriceWin results = client.sendEncryptedOffersProduct(offers);

        if(results==null){
            return;
        }

        byte[] price = SignatureUtil.objectToArrayByte(results.getObject());

        if(!SignatureUtil.verifyDataSignature(price, results.getObjectSigned(),results.getSignaturePubKey())){
            ui.tellFalsifiedSignatureManager();
            client.stopError();
            server.stopAllConnections(); //TODO : Trouver une meilleure fin d'enchères compromises pour les cas où la signature est usurpée.
        }
        else
        {
            byte[] signedPriceBySeller = SignatureUtil.signData(price, seller.getSignature());
            this.seller.setEndResults(new SigPack_Results(results, signedPriceBySeller, this.seller.getKey(),this.seller.getMyBid().getId()));

            this.setWinner(seller.getEndResults());

            SigPack_PriceWin p = (SigPack_PriceWin) seller.getEndResults().getObject();
            ui.tellWinnerBid( (double) p.getObject() );
            ui.tellResultsSend();
            seller.setBidResolved();
        }

    }

    public Map<PublicKey, PlayerStatus> getBiddersWinStatus(SigPack_Results results) {
        Set<SigPack_EncOffer> sigPackEncOffers = seller.getEncryptedOffersSet().getOffers();
        SigPack_PriceWin priceWin = (SigPack_PriceWin) results.getObject();

        boolean haveAWinner = false;
        Map<PublicKey, PlayerStatus> winStatusMap = new HashMap<>();
        for (SigPack_EncOffer sigPackEncOffer : sigPackEncOffers) {
            byte[] encPrice = (byte[]) sigPackEncOffer.getObject();

            if (Arrays.equals(encPrice,priceWin.getEncrypedPriceOrigin()) && !haveAWinner) {
                winStatusMap.put(sigPackEncOffer.getSignaturePubKey(), new PlayerStatus(results.getBidId(), true));
                haveAWinner = true;
            } else {
                winStatusMap.put(sigPackEncOffer.getSignaturePubKey(), new PlayerStatus(results.getBidId()));
            }
        }
        return winStatusMap;
    }

    public void setWinner(SigPack_Results winner) {

        seller.setWinStatusMap(getBiddersWinStatus(winner));
        seller.setResultsAreIn(true);
    }

    public void dispatchBidResults(){
        ui.tellWaitWinnerManifestation();
        server.addHandler(Headers.SET_WIN_EXP,new WinnerReplyer());

        while (!seller.isWinnerExpressed()) {
            waitSynchro(1000);
        }

        ui.displayEndBid(seller.getMyBid().getId());
    }

    public void displayHello() {
        ui.displayHello();
    }

    public void displayWinner() {
        Set<SigPack_EncOffer> sigPackEncOffers = seller.getEncryptedOffersSet().getOffers();
    }

}