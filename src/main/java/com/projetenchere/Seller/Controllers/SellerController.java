package com.projetenchere.Seller.Controllers;

import com.projetenchere.Seller.Model.Seller;
import com.projetenchere.Seller.View.ISellerUserInterface;
import com.projetenchere.Seller.View.commandLineInterface.SellerCommandLineInterface;
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
import java.security.SignatureException;
import java.time.LocalDateTime;
import java.util.*;

public class SellerController extends Controller {
    private final ISellerUserInterface ui = new SellerCommandLineInterface();
    private final SellerClient client = new SellerClient();
    private final Server server = new Server(24682);
    private final Seller seller = Seller.getInstance();
    private Bid myBid;
    private Winner winner = null;

    public SellerController() throws Exception {}

    public Bid getMyBid() {
        return myBid;
    }

    public void setWinner(Winner winner){
        this.winner = winner;
    }

    public ISellerUserInterface getUi() {
        return ui;
    }

    public void setSignatureConfig() throws Exception {
        setSignatureConfig(ui, seller);
    }

    public void createMyBid(){
        String id = UUID.randomUUID().toString();
        String name = ui.askBidName();
        String description = ui.askBidDescription();
        LocalDateTime end = ui.askBidEndTime();
        myBid = new Bid(id, name, description, end, new InetSocketAddress(NetworkUtil.getMyIP(), NetworkUtil.SELLER_PORT), seller.getKey());
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

    public void initServer() {

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
        Set<WinStatus> biddersWinStatus = getBiddersWinStatus();
    }

    public EncryptedOffersSet getEncryptedOffersSet(){
        return new EncryptedOffersSet(myBid.getId(), seller.getEncryptedOffers());
    }

    public void signEncryptedOffers() throws Exception {
        EncryptedOffersSet set = getEncryptedOffersSet();
        Set<EncryptedOffer> offers = set.getOffers();
        Set<EncryptedOffer> offersSigned = new HashSet<>();
        for (EncryptedOffer o : offers){
            offersSigned.add(new EncryptedOffer(seller.getSignature(),o.getPrice(),seller.getKey(),o.getBidId()));
        }
        seller.setEncryptedOffersSignedBySeller(new EncryptedOffersSet(set.getBidId() ,offersSigned));
    }

    public void sendEncryptedOffersSet() throws Exception {
        signEncryptedOffers();
        this.setWinner(client.sendEncryptedOffersSet(seller.getEncryptedOffersSignedBySeller()));
        Seller.getInstance().setResultsAreIn(true);
        ui.displayEncryptedOffersSetent();
    }

    public Set<WinStatus> getBiddersWinStatus(){
        Set<EncryptedOffer> encryptedOffers = seller.getEncryptedOffers();
        boolean haveAWinner = false;
        Set<WinStatus> winStatus = new HashSet<>();
        for (EncryptedOffer encryptedOffer : encryptedOffers) {
            if (Arrays.equals(encryptedOffer.getPrice(), winner.encryptedPrice()) && !haveAWinner) {
                winStatus.add(new WinStatus(winner.bidId(),true,winner.price()));
                haveAWinner = true;
            } else {
                winStatus.add(new WinStatus(winner.bidId(),false,-1));
            }
        }
        return winStatus;
    }
}