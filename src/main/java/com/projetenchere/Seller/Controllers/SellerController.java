package com.projetenchere.Seller.Controllers;

import com.projetenchere.Seller.Model.Seller;
import com.projetenchere.Seller.View.graphicalUserInterface.SellerGraphicalUserInterface;
import com.projetenchere.Seller.network.Handlers.ChecklistOkReplyer;
import com.projetenchere.Seller.network.Handlers.EncryptedOfferReplyer;
import com.projetenchere.Seller.network.Handlers.WinnerReplyer;
import com.projetenchere.Seller.network.SellerClient;
import com.projetenchere.common.Controllers.Controller;
import com.projetenchere.common.Models.PlayerStatus;
import com.projetenchere.common.Models.SignedPack.*;
import com.projetenchere.common.Utils.NetworkUtil;
import com.projetenchere.common.Utils.SignatureUtil;
import com.projetenchere.common.network.Headers;
import com.projetenchere.common.network.Server;

import java.net.InetSocketAddress;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.util.*;

public class SellerController extends Controller {
    private final SellerClient client = new SellerClient();
    private final Server server = new Server(24682);
    private final Seller seller = Seller.getInstance();
    SellerGraphicalUserInterface ui;

    public SellerController(SellerGraphicalUserInterface ui) {
        this.ui = ui;
    }

    public void setSignatureConfig() throws Exception {
        setSignatureConfig(ui, seller,"seller");
    }

    public void createMyBid() {
        seller.setMyBid(ui.askBid());
        seller.getMyBid().setSellerInformations(new InetSocketAddress(NetworkUtil.getMyIP(), NetworkUtil.SELLER_PORT));
        seller.getMyBid().setPubKeySignatureSeller(seller.getKey());
        seller.setEncryptedOffers(new Set_SigPackEncOffer(seller.getMyBid().getId(), new HashSet<>()));
        ui.displayBidCreated(seller.getMyBid());
    }

    public void sendMyBid() {
        client.connectToManager();
        if (seller.getMyBid() == null) throw new RuntimeException("No bid was registered");
        ui.tellSendBidToManager();
        client.sendBid(seller.getMyBid());
    }

    public boolean auctionInProgress() {
        return (!this.seller.getMyBid().isOver());
    }

    public void receiveOkUntilCheckEndAndSendResults() {
        server.start();
        server.addHandler(Headers.SEND_OFFER, new EncryptedOfferReplyer());

        while (auctionInProgress()) {
            waitSynchro(1000);
        }
        ui.addLogMessage("Enchère finie !");
        ui.addLogMessage("Envoie de la demande de résolution au gestionnaire.");
        server.addHandler(Headers.GET_RESULTS, new ChecklistOkReplyer());
        seller.resultsAreReady();
        server.removeHandler(Headers.SEND_OFFER);

        Map<PublicKey, byte[]> map = seller.getBidders();

        while (seller.getbiddersOk().containsAll(map.keySet())) {
            waitSynchro(1000);
        }

    }

    public void sendEncryptedOffersProduct() throws GeneralSecurityException {

        SigPack_EncOffersProduct offers = seller.getOffersProductSignedBySeller();
        ui.displayEncryptedOffersSet();

        SigPack_PriceWin results = client.sendEncryptedOffersProduct(offers);

        byte[] price = SignatureUtil.objectToArrayByte((double) results.getObject());
        if(!SignatureUtil.verifyDataSignature(price, results.getObjectSigned(),results.getSignaturePubKey())){
            ui.addLogMessage("Signature du gestionnaire invalide ! Enchères compromises !");
            //TODO S2 : Envoyer erreur aux B ?
            client.stopEverything();
        }
        else{
            byte[] signedPriceBySeller = SignatureUtil.signData(price, seller.getSignature());
            this.seller.setEndResults(new SigPack_Results(results, signedPriceBySeller, this.seller.getKey(),this.seller.getMyBid().getId()));

            this.setWinner(seller.getEndResults());

            SigPack_PriceWin p = (SigPack_PriceWin) seller.getEndResults().getObject();
            ui.addLogMessage("Le prix gagnant est " + (double) p.getObject() + "€");
            ui.addLogMessage("Résultats envoyés aux enchérisseurs.");
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

    public void receiveWinUntilPeriodEnd(){
        ui.addLogMessage("Attente qu'un gagnant se manifeste !");
        server.addHandler(Headers.SET_WIN_EXP,new WinnerReplyer());

        while (!seller.isWinnerExpressed()) {
            waitSynchro(1000);
        }

        ui.addLogMessage("Fin de l'enchère.");
    }

    public void displayHello() {
        ui.displayHello();
    }


    public void displayWinner() {
        Set<SigPack_EncOffer> sigPackEncOffers = seller.getEncryptedOffersSet().getOffers();
    }

}