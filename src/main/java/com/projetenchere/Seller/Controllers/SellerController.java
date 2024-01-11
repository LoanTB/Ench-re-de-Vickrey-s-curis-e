package com.projetenchere.Seller.Controllers;

import com.projetenchere.Seller.Model.Seller;
import com.projetenchere.Seller.View.ISellerUserInterface;
import com.projetenchere.Seller.View.commandLineInterface.SellerCommandLineInterface;
import com.projetenchere.Seller.network.Handlers.ChecklistOkReplyer;
import com.projetenchere.Seller.network.Handlers.EncryptedOfferReplyer;
import com.projetenchere.Seller.network.SellerClient;
import com.projetenchere.common.Models.Bid;
import com.projetenchere.common.Controllers.Controller;
import com.projetenchere.common.Models.Encrypted.EncryptedOffer;
import com.projetenchere.common.Models.Encrypted.EncryptedOffersSet;
import com.projetenchere.common.Models.Encrypted.SignedEncryptedOfferSet;
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
    private final ISellerUserInterface ui = new SellerCommandLineInterface();
    private final SellerClient client = new SellerClient();
    private final Server server = new Server(24682);
    private final Seller seller = Seller.getInstance();
    private Winner winner = null;

    public SellerController() throws Exception {
    }

    public void setWinner(Winner winner) {
        this.winner = winner;
        seller.setWinStatusMap(getBiddersWinStatus());
        seller.setResultsAreIn(true);
    }

    public ISellerUserInterface getUi() {
        return ui;
    }

    public void setSignatureConfig() throws Exception {
        setSignatureConfig(ui, seller);
    }

    public void createMyBid() {
        String id = UUID.randomUUID().toString();
        String name = ui.askBidName();
        String description = ui.askBidDescription();
        LocalDateTime end = ui.askBidEndTime();
        seller.setMyBid(new Bid(id, name, description, end, new InetSocketAddress(NetworkUtil.getMyIP(), NetworkUtil.SELLER_PORT), seller.getKey()));
        seller.setEncryptedOffers(new EncryptedOffersSet(seller.getMyBid().getId(), new HashSet<>()));
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
        ui.waitOk();
        server.start();
        server.addHandler(Headers.SEND_OFFER, new EncryptedOfferReplyer());

        while (auctionInProgress()) {
            waitSynchro(1000);
        }
        server.addHandler(Headers.GET_WIN_STATUS, new ChecklistOkReplyer());
        seller.resultsAreReady();
        server.removeHandler(Headers.SEND_OFFER);

        Map<PublicKey, byte[]> map = seller.getBidders();

        while (seller.getbiddersOk().containsAll(map.keySet())){
            waitSynchro(1000);
        }

        //server.removeHandler(Headers.GET_WIN_STATUS);
    }

    public void sendEncryptedOffersSet() throws Exception {
        SignedEncryptedOfferSet offers = seller.getEncryptedOffersSignedBySeller();
        ui.displayEncryptedOffersSet();
        this.setWinner(client.sendEncryptedOffersSet(offers));
    }

    public void displayHello() {
        ui.displayHello();
    }

    public void displayOfferReceived() {
        ui.displayOfferReceived();
    }

    public void displayWinner() {
        Set<EncryptedOffer> encryptedOffers = seller.getEncryptedOffersSet().getOffers();
    }

    public Map<PublicKey, WinStatus> getBiddersWinStatus() {
        Set<EncryptedOffer> encryptedOffers = seller.getEncryptedOffersSet().getOffers();
        boolean haveAWinner = false;
        Map<PublicKey, WinStatus> winStatusMap = new HashMap<>();
        for (EncryptedOffer encryptedOffer : encryptedOffers) {
            if (Arrays.equals(encryptedOffer.getPrice(), winner.encryptedPrice()) && !haveAWinner) {
                winStatusMap.put(encryptedOffer.getSignaturePublicKey(), new WinStatus(winner.bidId(), true, winner.price()));
                haveAWinner = true;
            } else {
                winStatusMap.put(encryptedOffer.getSignaturePublicKey(), new WinStatus(winner.bidId(), false, -1));
            }
        }
        return winStatusMap;
    }
}