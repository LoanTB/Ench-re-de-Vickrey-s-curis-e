package com.projetenchere.Seller.Controllers;

import com.projetenchere.Seller.Model.Seller;
import com.projetenchere.Seller.View.ISellerUserInterface;
import com.projetenchere.Seller.View.commandLineInterface.SellerCommandLineInterface;
import com.projetenchere.common.Models.Bid;
import com.projetenchere.common.Controllers.Controller;
import com.projetenchere.common.Models.Encrypted.EncryptedOffer;
import com.projetenchere.common.Models.Encrypted.EncryptedPrices;
import com.projetenchere.common.Models.Network.Communication.Winner;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

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

    public void initConnexion() {
        Thread thread = new Thread(networkController);
        thread.start();
    }

    public void createMyBid(){
        String id = UUID.randomUUID().toString();
        String name = ui.askBidName();
        String description = ui.askBidDescription();
        LocalDateTime end = ui.askBidEndTime();
        this.myBid = new Bid(id, name, description, end, networkController.getMyPublicInformations());
    }

    public boolean auctionInProgress(){
        return (!this.myBid.isOver());
    }

    public void receiveOffersUntilBidEnd() throws IOException {
        ui.waitOffers();
        while (auctionInProgress()) {
            try {
                wait(1000); // Eviter une utilisation excessive du CPU
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveEncryptedOffer(EncryptedOffer encryptedOffer, String bidderIp, int bidderPort){
        seller.addEncryptedOffer(encryptedOffer);
        seller.addBidderIp(bidderIp);
        seller.addBidderPort(bidderPort);
        displayOfferReceived(encryptedOffer);
    }

    public void displayHello(){ui.displayHello();}

    public void initContactWithManager() throws Exception {
        networkController.sendMySI("Manager");
        while (networkController.informationContainsPublicKeys("Manager")) {
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

    public void displayEncryptedPriceSended(){
        ui.displayEncryptedPriceSended();
    }

    public void displayResultsSent(){
        ui.displayResultsSent();
    }

    public EncryptedPrices getEncryptedPrices(List<EncryptedOffer> encryptedOffers){
        Set<byte[]> encryptedPrices = new HashSet<>();
        for (EncryptedOffer encryptedOffer: encryptedOffers){
            encryptedPrices.add(encryptedOffer.getPrice());
        }
        return new EncryptedPrices(myBid.getId(),encryptedPrices);
    }

    public void sendEncryptedPrices() throws Exception {
        networkController.sendEncryptedPrices(getEncryptedPrices(seller.getEncryptedOffers()));
    }

    public void waitFetchWinner() {
        while (winner == null) {
            try {
                wait(1000); // Eviter une utilisation excessive du CPU
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendResults() throws IOException {
        if (seller.getBiddersIps().size() == seller.getBiddersPorts().size() && seller.getBiddersIps().size() == getBiddersWinStatus().size()){
            networkController.sendResults(seller.getBiddersIps(),seller.getBiddersPorts(),getBiddersWinStatus());
        } else {
            throw new IllegalArgumentException("Les liste de contacts enchérisseur et liste des gagnants ne concordent pas");
        }
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