package com.projetenchere.Bidder.Controller;

import com.projetenchere.Bidder.Controller.network.BidderNetworkController;
import com.projetenchere.Bidder.View.IBidderUserInterface;
import com.projetenchere.Bidder.View.commandLineInterface.BidderCommandLineInterface;
import com.projetenchere.common.Model.Bid;
import com.projetenchere.common.Model.BidStarter;
import com.projetenchere.common.Model.Offer;

import java.io.IOException;
import java.security.PublicKey;

public class BidderController {
    public final IBidderUserInterface ui = new BidderCommandLineInterface();
    public final BidderNetworkController network = new BidderNetworkController();
    public String sellerIP;
    public PublicKey publicKey;
    public Bid currentBid;


    public Offer readOfferFromInterface() {
        return ui.readOffer();
    }

    public void loadInitPackage() throws IOException, ClassNotFoundException {
         BidStarter bidStart = network.askForInitPackage();
         this.currentBid = bidStart.getCurrentBid();
         this.publicKey = bidStart.getManagerPublicKey();


    }

    public boolean askSellerIfAlreadySentOffer() {
        //TODO: ask seller over network
        return true;
    }

    public void showBid() {
        ui.displayBid(this.currentBid);
    }

    public void readAndSendOffer() throws IOException {
        Offer offer = readOfferFromInterface();
        network.sendOffer(offer, sellerIP);
    }

    public void waitForPrice() throws IOException, ClassNotFoundException {
         int priceToPay = network.fetchPrice();
         if (priceToPay == -1) {
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
        this.sellerIP = bidStarter.getSellerAdress();
    }
}
