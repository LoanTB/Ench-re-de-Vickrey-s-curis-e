package com.projetenchere.Seller.Controllers;

import com.projetenchere.Seller.Model.Seller;
import com.projetenchere.Seller.View.ISellerUserInterface;
import com.projetenchere.Seller.View.commandLineInterface.SellerCommandLineInterface;
import com.projetenchere.common.Models.Bid;
import com.projetenchere.common.Controllers.Controller;
import com.projetenchere.common.Models.Encrypted.EncryptedOffer;
import com.projetenchere.common.Models.Encrypted.EncryptedPrices;
import com.projetenchere.common.Models.Identity;
import com.projetenchere.common.Models.Network.Communication.WinStatus;
import com.projetenchere.common.Models.Network.Communication.Winner;

import java.time.LocalDateTime;
import java.util.*;

import static java.lang.Thread.sleep;

public class SellerController extends Controller {
    SellerNetworkController networkController = new SellerNetworkController(this);
    private final ISellerUserInterface ui = new SellerCommandLineInterface();
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

    public ISellerUserInterface getUi() {
        return ui;
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

    public void sendMyBid() throws Exception {
        ui.tellSendBidToManager();
        networkController.sendTo("Manager",myBid);
    }

    public boolean auctionInProgress(){
        return (!this.myBid.isOver());
    }

    public void receiveOffersUntilBidEnd() {
        ui.waitOffers();
        while (auctionInProgress()) {
            waitSychro(1000);
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
            waitSychro(1000);
        }
    }

    public void displayOfferReceived(EncryptedOffer encryptedOffer){
        ui.displayOfferReceived(encryptedOffer);
    }

    public void displayWinner(){
        List<EncryptedOffer> encryptedOffers = seller.getEncryptedOffers();
        List<WinStatus> biddersWinStatus = getBiddersWinStatus();
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
            waitSychro(1000);
        }
    }

    public void sendResults() throws Exception {
        List<WinStatus> results = getBiddersWinStatus();
        List<String> ids = seller.getBiddersIds();
        if (ids.size() != results.size()){
            throw new IllegalArgumentException("Les liste de contacts enchérisseur et liste des gagnants ne concordent pas");
        }
        for (int i=0;i<ids.size();i++){
            networkController.sendTo(ids.get(i),results.get(i));
        }
        ui.displayResultsSent();
    }

    public List<WinStatus> getBiddersWinStatus(){
        List<EncryptedOffer> encryptedOffers = seller.getEncryptedOffers();
        boolean haveAWinner = false;
        List<WinStatus> winStatus = new ArrayList<>();
        for (EncryptedOffer encryptedOffer : encryptedOffers) {
            if (Arrays.equals(encryptedOffer.getPrice(), winner.getEncryptedId()) && !haveAWinner) {
                winStatus.add(new WinStatus(winner.getBidId(),true,winner.getPrice()));
                haveAWinner = true;
            } else {
                winStatus.add(new WinStatus(winner.getBidId(),false,-1));
            }
        }
        return winStatus;
    }

    public String getWinnerID(List<EncryptedOffer> encryptedOffers, List<WinStatus> biddersWinStatus){
        for (int i=0;i<encryptedOffers.size();i++){
            if (biddersWinStatus.get(i).isWinner()){
                return encryptedOffers.get(i).getIdBidder();
            }
        }
        return null;
    }

}