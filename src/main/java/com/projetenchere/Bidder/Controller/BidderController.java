package com.projetenchere.Bidder.Controller;

import com.projetenchere.Bidder.Controller.Network.BidderNetworkController;
import com.projetenchere.Bidder.Model.Bidder;
import com.projetenchere.Bidder.View.IBidderUserInterface;
import com.projetenchere.Bidder.View.commandLineInterface.BidderCommandLineInterface;
import com.projetenchere.common.Models.Controller;
import com.projetenchere.common.Models.Network.Communication.CurrentBids;
import com.projetenchere.common.Models.Encrypted.EncryptedOffer;
import com.projetenchere.common.Models.Network.Communication.WinStatus;
import com.projetenchere.common.Models.Offer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BidderController extends Controller {
    private final IBidderUserInterface ui = new BidderCommandLineInterface();
    private final BidderNetworkController networkController = new BidderNetworkController(this);

    private CurrentBids currentBids;
    private final List<Integer> participatedBid = new ArrayList<>();
    private final List<WinStatus> results = new ArrayList<>();
    private final Bidder bidder = new Bidder();

    public BidderController() throws Exception {}

    public void setCurrentBids(CurrentBids currentBids) {
        this.currentBids = currentBids;
    }

    public void addResult(WinStatus result){
        results.add(result);
    }

    public List<Integer> getParticipatedBid(){
        return participatedBid;
    }

    public Offer readOfferFromInterface(){
        return ui.readOffer(bidder);
    }

    public void readPort() {
        this.bidder.setPort(ui.readPort());
    }

    public void showBid() {
        ui.displayBid(this.currentBids);
    }

    public void readName() {
        this.bidder.setId(ui.readName());
    }

    public void readAndSendOffer() throws Exception {
        Offer offer = readOfferFromInterface();
        EncryptedOffer encryptedOffer = new EncryptedOffer(offer, networkController.getManagerInformations().getSignaturePublicKey());
        networkController.sendOffer(encryptedOffer);
        ui.tellOfferSent();
    }

    public void waitForPrice() {
        ui.tellWaitOfferResult();
        while (results.isEmpty()) {
            try {
                wait(1000); // Eviter une utilisation excessive du CPU
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (results.get(0).isWinner()){
            ui.tellOfferWon(results.get(0).getPrice());
        } else {
            ui.tellOfferLost();
        }
    }

    public void sendBidderInfosToManager() throws IOException, ClassNotFoundException {
        networkController.sendBidderInfosToManager();
    }
}
