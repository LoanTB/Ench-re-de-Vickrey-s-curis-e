package com.projetenchere.Seller.Controllers;

import com.projetenchere.Seller.Model.Seller;
import com.projetenchere.Seller.View.ISellerUserInterface;
import com.projetenchere.Seller.View.commandLineInterface.SellerCommandLineInterface;
import com.projetenchere.common.Models.Bid;
import com.projetenchere.common.Controllers.Controller;
import com.projetenchere.common.Models.Encrypted.EncryptedOffer;
import com.projetenchere.common.Models.Encrypted.EncryptedPrices;
import com.projetenchere.common.Models.Identity;
import com.projetenchere.common.Models.Network.Communication.Winner;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import static java.lang.Thread.sleep;

public class SellerController extends Controller {
    SellerNetworkController networkController = new SellerNetworkController(this);
    private static final ISellerUserInterface ui = new SellerCommandLineInterface();
    private final Seller seller = new Seller();
    private Bid myBid;
    private Winner winner = null;

    public SellerController() throws Exception {}

    public Bid getMyBid() {
        return myBid;
    }

    public void setWinner(Winner winner){
        this.winner = winner;
    }

    public void networkListeningInitialization() {
        Thread thread = new Thread(networkController);
        thread.start();
    }

    public void createMyBid(){
        String id = UUID.randomUUID().toString();
        String name = ui.askBidName();
        String description = ui.askBidDescription();
        LocalDateTime end = ui.askBidEndTime();
        myBid = new Bid(id, name, description, end, networkController.getMyPublicInformations());
        ui.displayBidCreated(myBid);
    }

    public boolean auctionInProgress(){
        return (!this.myBid.isOver());
    }

    public void receiveOffersUntilBidEnd(){
        ui.waitOffers();
        while (auctionInProgress()) {
            try {
                wait(1000); // Eviter une utilisation excessive du CPU
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveEncryptedOffer(EncryptedOffer encryptedOffer, String bidderId, String bidderIp, int bidderPort){
        seller.addBidderId(bidderId);
        seller.addEncryptedOffer(encryptedOffer);
        seller.addBidderIp(bidderIp);
        seller.addBidderPort(bidderPort);
        displayOfferReceived(encryptedOffer);
    }

    public void displayHello(){ui.displayHello();}

    public void readInfos() throws Exception {
        seller.setIdentity(new Identity(UUID.randomUUID().toString(),ui.readName(),ui.readSurname(),"Seller"));
        networkController.setMyInformationsWithPort(seller.getIdentity(),ui.readPort());
        seller.setInformations(networkController.getMyPublicInformations());
    }

    public void sendSellerInfosToManager() throws Exception {
        networkListeningInitialization();
        networkController.sendMySI("Manager");
        boolean succes;
        try {
            networkController.sendMySI("Manager");
            succes = true;
        } catch (Exception ignore){
            succes = false;
            ui.tellWaitManager();
        }
        while (!succes) {
            sleep(1000);
            try {
                networkController.sendMySI("Manager");
                succes = true;
                ui.tellManagerFound();
            } catch (Exception ignore){
                succes = false;
            }
        }
    }

    public void waitManagerContactKeys(){
        if (networkController.informationContainsPublicKeys("Manager")){
            return;
        }
        ui.tellWaitManagerSecurityInformations();
        while (!networkController.informationContainsPublicKeys("Manager")) {
            try {
                wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void displayOfferReceived(EncryptedOffer encryptedOffer){
        ui.displayOfferReceived(encryptedOffer);
    }

    public void displayWinner(){
        List<EncryptedOffer> encryptedOffers = seller.getEncryptedOffers();
        List<Double> biddersWinStatus = getBiddersWinStatus();
        String winnerID = getWinnerID(encryptedOffers,biddersWinStatus);
        Double price = winner.getPrice();
        ui.displayWinner(winnerID,price);
    }

    public EncryptedPrices getEncryptedPrices(List<EncryptedOffer> encryptedOffers){
        Set<byte[]> encryptedPrices = new HashSet<>();
        for (EncryptedOffer encryptedOffer: encryptedOffers){
            encryptedPrices.add(encryptedOffer.getPrice());
        }
        return new EncryptedPrices(myBid.getId(),encryptedPrices);
    }

    public void sendEncryptedPrices() throws Exception {
        networkController.sendTo("Manager",getEncryptedPrices(seller.getEncryptedOffers()));
        ui.displayEncryptedPriceSended();
    }

    public void waitFetchWinner() {
        ui.tellWaitWinnerDeclaration();
        while (winner == null) {
            try {
                wait(1000); // Eviter une utilisation excessive du CPU
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendResults() throws Exception {
        List<Double> results = getBiddersWinStatus();
        List<String> ids = seller.getBiddersIps();
        if (ids.size() != results.size()){
            throw new IllegalArgumentException("Les liste de contacts enchérisseur et liste des gagnants ne concordent pas");
        }
        for (int i=0;i<ids.size();i++){
            networkController.sendTo(ids.get(i),results.get(i));
        }
        ui.displayResultsSent();
    }

    public List<Double> getBiddersWinStatus(){
        List<EncryptedOffer> encryptedOffers = seller.getEncryptedOffers();
        boolean haveAWinner = false;
        List<Double> winStatus = new ArrayList<>();
        for (EncryptedOffer encryptedOffer : encryptedOffers) {
            if (Arrays.equals(encryptedOffer.getPrice(), winner.getEncryptedId()) && !haveAWinner) {
                winStatus.add(winner.getPrice());
                haveAWinner = true;
            } else {
                winStatus.add(-1.0);
            }
        }
        return winStatus;
    }

    public String getWinnerID(List<EncryptedOffer> encryptedOffers, List<Double> biddersWinStatus){
        for (int i=0;i<encryptedOffers.size();i++){
            if (biddersWinStatus.get(i) != -1.0){
                return encryptedOffers.get(i).getIdBidder();
            }
        }
        return null;
    }

}