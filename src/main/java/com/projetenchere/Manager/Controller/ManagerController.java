package com.projetenchere.Manager.Controller;

import com.projetenchere.Manager.Controller.Network.ManagerNetworkController;
import com.projetenchere.Manager.Model.Manager;
import com.projetenchere.Manager.View.IManagerUserInterface;
import com.projetenchere.Manager.View.commandLineInterface.ManagerCommandLineInterface;
import com.projetenchere.common.Models.Bid;
import com.projetenchere.common.Models.Controller;
import com.projetenchere.common.Models.Encrypted.EncryptedPrices;
import com.projetenchere.common.Models.Network.Communication.CurrentBids;
import com.projetenchere.common.Models.Network.Communication.Winner;
import com.projetenchere.common.Utils.EncryptionUtil;

import java.io.IOException;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

public class ManagerController extends Controller {
    public final IManagerUserInterface ui = new ManagerCommandLineInterface();
    public final ManagerNetworkController networkController = new ManagerNetworkController(this);

    public final Manager manager = new Manager();
    private CurrentBids currentBids = null;

    public ManagerController() throws Exception {}

    private Bid createBid() {
        int id = ui.askBidId();
        String name = ui.askBidName();
        String description = ui.askBidDescription();
        LocalDateTime end = ui.askBidEndTime();
        return new Bid(id, name, description, end, networkController.getMyInformations());
    }




    public Bid initBid() throws IOException {
        Bid bid = createBid();
        System.out.println("Vous avez créé l'enchère : ");
        System.out.println(bid._toString());
        addBid(bid);
        networkController.sendBidToSeller(bid);
        return bid;
    }

    public CurrentBids getCurrentBids() {
        return currentBids;
    }

    public void addBid(Bid bid){
        currentBids.addCurrentBid(bid);
    }

    public void startAllBids(){
        currentBids.startAllBids();
    }

    public void startBid(int id) {
        currentBids.startBids(id);
    }

    public void generateManagerKeys() throws Exception {
        manager.setManagerKeys(EncryptionUtil.generateKeyPair());
        currentBids = new CurrentBids();
    }

    public void initConnexion() {
        Thread thread = new Thread(networkController);
        thread.start();
    }

    public void launchBids(){
        startAllBids();
    }

    public void launchBid(int id){
        startBid(id);
    }

    public Winner processPrices(EncryptedPrices encryptedPrices) throws Exception {
        double price1 = 0;
        byte[] encrypted1 = null;
        double decrypted;
        for (byte[] encrypted : encryptedPrices.getPrices()) {
            decrypted = EncryptionUtil.decrypt(encrypted,manager.getManagerPrivateKey());
            if (decrypted > price1){
                price1 = decrypted;
                encrypted1 = encrypted;
            }
        }
        double price2 = -1;
        for (byte[] encrypted : encryptedPrices.getPrices()) {
            decrypted = EncryptionUtil.decrypt(encrypted,manager.getManagerPrivateKey());
            if (decrypted > price2 && decrypted != price1){
                price2 = decrypted;
            }
        }
        if (price2 == -1){
            price2 = price1;
        }
        Winner winner = new Winner(encryptedPrices.getBidId(), encrypted1,price2);
        showWinnerPrice(winner);
        return winner;
    }

    public void showWinnerPrice(Winner winner) {
        ui.displayWinnerPrice(winner);
    }

    public void displayHello(){
        ui.displayHello();
    }

    public void displayGenerateKey() {
        ui.displayGenerateKey();
    }

    public void displayBidLaunch() {
        ui.displayBidLaunch();
    }

    public void displayReceivedPrices() {
        ui.displayReceivedPrices();
    }

    public void displayPriceProcessing() {
        ui.displayPriceProcessing();
    }

    public void displaySentWinnerPrice() {
        ui.displaySentWinnerPrice();
    }

    public void displayEndOfAuction() {
        ui.displayEndOfAuction();
    }

}