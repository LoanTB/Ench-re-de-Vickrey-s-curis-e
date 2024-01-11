package com.projetenchere.Seller.Controllers;

import com.projetenchere.Manager.View.IManagerUserInterface;
import com.projetenchere.Manager.View.graphicalUserInterface.ManagerGraphicalUserInterface;
import com.projetenchere.Seller.Model.Seller;
import com.projetenchere.Seller.View.ISellerUserInterface;
import com.projetenchere.Seller.View.graphicalUserInterface.SellerGraphicalUserInterface;
import com.projetenchere.Seller.network.Handlers.EncryptedOfferReplyer;
import com.projetenchere.Seller.network.SellerClient;
import com.projetenchere.common.Models.Bid;
import com.projetenchere.common.Controllers.Controller;
import com.projetenchere.common.Models.Encrypted.EncryptedOffer;
import com.projetenchere.common.Models.Encrypted.EncryptedOffersSet;
import com.projetenchere.common.Models.WinStatus;
import com.projetenchere.common.Models.Winner;
import com.projetenchere.common.Utils.NetworkUtil;
import com.projetenchere.common.network.Headers;
import com.projetenchere.common.network.Server;

import java.net.InetSocketAddress;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.util.*;

public class SellerController extends Controller {
    private final SellerClient client = new SellerClient();
    private final Server server = new Server(24682);
    private final Seller seller = Seller.getInstance();
    private Bid myBid;
    private Winner winner = null;
    SellerGraphicalUserInterface ui;

    public SellerController(SellerGraphicalUserInterface ui) {
        this.ui = ui;
    }

    public Bid getMyBid() {
        return myBid;
    }

    public void setWinner(Winner winner){
        this.winner = winner;
        seller.setWinStatusMap(getBiddersWinStatus());
        seller.setResultsAreIn(true);
    }

    public void setSignatureConfig() throws Exception {
        setSignatureConfig(ui, seller);
    }

    public void createMyBid(){
        myBid = ui.askBid();
        myBid.startBid();
        myBid.setSellerInformations(new InetSocketAddress(NetworkUtil.getMyIP(), NetworkUtil.SELLER_PORT));
        myBid.setPubKeySignatureSeller(seller.getKey());
        seller.setEncryptedOffersSignedBySeller(new EncryptedOffersSet(myBid.getId(),new HashSet<>()));
        ui.displayBidCreated(myBid);
    }

    public void sendMyBid() {
        client.connectToManager();
        if (myBid == null) throw new RuntimeException("No bid was registered");
        ui.tellSendBidToManager();
        client.sendBid(myBid);
    }

    public boolean auctionInProgress(){
        return (!this.myBid.isOver());
    }

    public void receiveOffersUntilBidEndAndSendResults() {
        ui.waitOffers();
        server.addHandler(Headers.GET_WIN_STATUS, new EncryptedOfferReplyer());
        server.start();
        while (auctionInProgress()) {
            waitSynchro(1000);
        }
        server.removeHandler(Headers.GET_WIN_STATUS);
    }

    public void displayHello(){ui.displayHello();}


    public void displayOfferReceived(){
        ui.displayOfferReceived();
    }

    public void displayWinner(){
        Set<EncryptedOffer> encryptedOffers = seller.getEncryptedOffers();
    }

    public EncryptedOffersSet getEncryptedOffersSet(){
        return new EncryptedOffersSet(myBid.getId(), seller.getEncryptedOffers());
    }

    public EncryptedOffersSet reSignedEncryptedOffers() throws Exception {
        EncryptedOffersSet set = getEncryptedOffersSet();
        Set<EncryptedOffer> offers = set.getOffers();
        Set<EncryptedOffer> offersSigned = new HashSet<>();
        for (EncryptedOffer o : offers){
            offersSigned.add(new EncryptedOffer(seller.getSignature(),o.getPrice(),seller.getKey(),o.getBidId()));
        }
        return new EncryptedOffersSet(set.getBidId() ,offersSigned);
    }

    public void sendEncryptedOffersSet() throws Exception {
        this.setWinner(client.sendEncryptedOffersSet(reSignedEncryptedOffers()));
        ui.displayEncryptedOffersSetent();
    }

    public Map<PublicKey, WinStatus> getBiddersWinStatus(){
        Set<EncryptedOffer> encryptedOffers = seller.getEncryptedOffers();
        boolean haveAWinner = false;
        Map<PublicKey, WinStatus> winStatusMap = new HashMap<>();
        for (EncryptedOffer encryptedOffer : encryptedOffers) {
            if (Arrays.equals(encryptedOffer.getPrice(), winner.encryptedPrice()) && !haveAWinner) {
                winStatusMap.put(encryptedOffer.getSignaturePublicKey(),new WinStatus(winner.bidId(),true,winner.price()));
                haveAWinner = true;
            } else {
                winStatusMap.put(encryptedOffer.getSignaturePublicKey(),new WinStatus(winner.bidId(),false,-1));
            }
        }
        return winStatusMap;
    }
}