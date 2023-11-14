package com.projetenchere.Bidder.Controller;

import com.projetenchere.Bidder.Model.Bidder;
import com.projetenchere.Bidder.View.IBidderUserInterface;
import com.projetenchere.Bidder.View.commandLineInterface.BidderCommandLineInterface;
import com.projetenchere.common.Models.Bid;
import com.projetenchere.common.Models.Network.Communication.CurrentBids;
import com.projetenchere.common.Models.Encrypted.EncryptedOffer;
import com.projetenchere.common.Models.Offer;

import java.io.IOException;
import java.security.PublicKey;

public class BidderController {
    private final IBidderUserInterface ui = new BidderCommandLineInterface();
    private final BidderNetworkController network = new BidderNetworkController();


    private String sellerIP;
    private PublicKey publicKey;
    private Bid currentBid;
    private final Bidder bidder = new Bidder();


    public Offer readOfferFromInterface(){
        return ui.readOffer(bidder);
    }

    public void readPort() {
        this.bidder.setPort(ui.readPort());
    }

    public void showBid() {
        ui.displayBid(this.currentBid);
    }

    public void readName() {
        this.bidder.setId(ui.readName());
    }

    public void readAndSendOffer() throws Exception {
        Offer offer = readOfferFromInterface();
        EncryptedOffer encryptedOffer = new EncryptedOffer(offer, publicKey);
        network.sendOffer(encryptedOffer, sellerIP, bidder.getPort());
        ui.tellOfferSent();
    }

    public void waitForPrice() throws IOException, ClassNotFoundException {
        ui.tellWaitOfferResult();
         double priceToPay = network.fetchPrice(bidder.getPort());
         //System.out.println(priceToPay);
         if (priceToPay < 0D){
             ui.tellOfferLost();
         }
         else {
             ui.tellOfferWon(priceToPay);
         }

    }

    public void fetchInitPackage() throws IOException, ClassNotFoundException {
        CurrentBids currentBids = network.askForInitPackage(bidder.getPort());
        this.currentBid = currentBids.getCurrentBid();
        this.publicKey = currentBids.getManagerPublicKey();
        this.sellerIP = currentBids.getSellerAddress();
    }
}
