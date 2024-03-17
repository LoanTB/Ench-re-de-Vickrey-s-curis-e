package com.projetenchere.Seller.Controllers;

import com.projetenchere.Seller.Model.Seller;
import com.projetenchere.Seller.View.graphicalUserInterface.SellerGraphicalUserInterface;
import com.projetenchere.Seller.network.Handlers.ChecklistOkReplyer;
import com.projetenchere.Seller.network.Handlers.EncryptedOfferReplyer;
import com.projetenchere.Seller.network.Handlers.WinnerReplyer;
import com.projetenchere.Seller.network.SellerClient;
import com.projetenchere.common.Controllers.Controller;
import com.projetenchere.common.Models.PlayerStatus.PlayerStatus;
import com.projetenchere.common.Models.SignedPack.*;
import com.projetenchere.common.Models.Winner;
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
    private Winner winner = null;
    private SigPack_Results EndResults = null;

    public SellerController(SellerGraphicalUserInterface ui) {
        this.ui = ui;
    }

    public void setSignatureConfig() throws Exception {
        setSignatureConfig(ui, seller);
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
        server.addHandler(Headers.GET_PLAYER_STATUS, new ChecklistOkReplyer());
        seller.resultsAreReady();
        server.removeHandler(Headers.SEND_OFFER);

        Map<PublicKey, byte[]> map = seller.getBidders();

        while (seller.getbiddersOk().containsAll(map.keySet())) {
            waitSynchro(1000);
        }

    }

    public void setWinner(Winner winner) {
        this.winner = winner;
        seller.setWinStatusMap(getBiddersWinStatus());
        seller.setResultsAreIn(true);
    }

    public void sendEncryptedOffersSet() throws GeneralSecurityException { //TODO : CHANGER !
        //TODO : Rename la méthode
        SigPack_EncOffersProduct offers = seller.getOffersProductSignedBySeller();
        ui.displayEncryptedOffersSet();

        SigPack_PriceWin results = client.sendEncryptedOffersSet(offers);

        byte[] price = SignatureUtil.objectToArrayByte(results.getObject());
        if(!SignatureUtil.verifyDataSignature(price, results.getObjectSigned(),results.getSignaturePubKey())){
            ui.addLogMessage("Signature du gestionnaire invalide ! Enchères compromises !");
            //TODO : Envoyer erreur aux B ?
            client.stopEverything();
        }
        else{

            byte[] signedPriceBySeller = SignatureUtil.signData(price, seller.getSignature());
            this.EndResults = new SigPack_Results(results, signedPriceBySeller, this.seller.getKey());

            ui.addLogMessage("Le prix gagnant est " + winner.price() + "€");
            ui.addLogMessage("Résultats envoyés aux enchérisseurs."); //??? TODO : A déplacer.
        }

    }


    //Ajouter l'étape de réception de la manifestation de l'enchérisseur gagnant.
    //DataWrapper pour recevoir le "IWIN"
    public void receiveWinUntilPeriodEnd(){
//TODO : Recevoir manifestation.
            ui.addLogMessage("Attente qu'un gagnant se manifeste !");
            server.addHandler(Headers.GET_WIN_EXP,new WinnerReplyer());

         //   this.setWinner();

    }

    public void displayHello() {
        ui.displayHello();
    }


    public void displayWinner() {
        Set<SigPack_EncOffer> sigPackEncOffers = seller.getEncryptedOffersSet().getOffers();
    }

    public Map<PublicKey, PlayerStatus> getBiddersWinStatus() { //TODO :
        Set<SigPack_EncOffer> sigPackEncOffers = seller.getEncryptedOffersSet().getOffers();
        boolean haveAWinner = false;
        Map<PublicKey, PlayerStatus> winStatusMap = new HashMap<>();
        for (SigPack_EncOffer sigPackEncOffer : sigPackEncOffers) {
            byte[] encPrice = SignatureUtil.objectToArrayByte(sigPackEncOffer.getObject());

            if (Arrays.equals(encPrice, winner.encryptedPrice()) && !haveAWinner) {
                winStatusMap.put(sigPackEncOffer.getSignaturePubKey(), new PlayerStatus(winner.bidId(), winner.price(), true));
                haveAWinner = true;
            } else {
                winStatusMap.put(sigPackEncOffer.getSignaturePubKey(), new PlayerStatus(winner.bidId(), -1));
            }
        }
        return winStatusMap;
    }


}