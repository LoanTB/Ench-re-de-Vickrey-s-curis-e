package com.projetenchere.Bidder.Controller;

import com.projetenchere.Bidder.Controller.network.BidderNetworkController;
import com.projetenchere.Bidder.Model.Bidder;
import com.projetenchere.Bidder.View.IBidderUserInterface;
import com.projetenchere.Bidder.View.commandLineInterface.BidderCommandLineInterface;
import com.projetenchere.common.Model.Bid;
import com.projetenchere.common.Model.BidStarter;
import com.projetenchere.common.Model.Encrypted.EncryptedOffer;
import com.projetenchere.common.Model.Offer;

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

    public void showBid() {
        ui.displayBid(this.currentBid);
    }

    public void readName() {
        this.bidder.setId(ui.readName());
    }

    public void readAndSendOffer() throws Exception {
        Offer offer = readOfferFromInterface();
        EncryptedOffer encryptedOffer = new EncryptedOffer(offer, publicKey);
        network.sendOffer(encryptedOffer, sellerIP);
    }

    public void waitForPrice() throws IOException, ClassNotFoundException {
         double priceToPay = network.fetchPrice();
         System.out.println(priceToPay);
         if (priceToPay < 0D){
             ui.tellOfferLost();
         }
         else {
             ui.tellOfferWon(priceToPay);
         }

    }

    public void fetchInitPackage() throws IOException, ClassNotFoundException {
        BidStarter bidStarter = network.askForInitPackage();
        this.currentBid = bidStarter.getCurrentBid();
        this.publicKey = bidStarter.getManagerPublicKey();
        this.sellerIP = bidStarter.getSellerAddress();
    }
}
