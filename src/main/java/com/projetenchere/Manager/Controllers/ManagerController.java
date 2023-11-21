package com.projetenchere.Manager.Controllers;

import com.projetenchere.Manager.Model.Manager;
import com.projetenchere.Manager.View.IManagerUserInterface;
import com.projetenchere.Manager.View.commandLineInterface.ManagerCommandLineInterface;
import com.projetenchere.common.Models.Bid;
import com.projetenchere.common.Controllers.Controller;
import com.projetenchere.common.Models.Encrypted.EncryptedPrices;
import com.projetenchere.common.Models.Network.Communication.CurrentBids;
import com.projetenchere.common.Models.Network.Communication.Winner;
import com.projetenchere.common.Utils.EncryptionUtil;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

public class ManagerController extends Controller {
    public final IManagerUserInterface ui = new ManagerCommandLineInterface();
    public final ManagerNetworkController networkController = new ManagerNetworkController(this);

    public final Manager manager = new Manager();
    private final CurrentBids currentBids = new CurrentBids();

    public ManagerController() throws Exception {}

    private Bid createBid() {
        String id = UUID.randomUUID().toString();
        String name = ui.askBidName();
        String description = ui.askBidDescription();
        LocalDateTime end = ui.askBidEndTime();
        return new Bid(id, name, description, end, networkController.getMyPublicInformations());
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

    public void startBid(String idBid) {
        currentBids.startBids(idBid);
    }

    public void generateManagerKeys() throws Exception {
        ui.displayGenerateKey();
        manager.setManagerKeys(EncryptionUtil.generateKeyPair());
    }

    public void initConnexion() {
        Thread thread = new Thread(networkController);
        thread.start();
        ui.tellManagerReadyToProcessBids();
    }

    public void launchBids(){
        ui.displayBidLaunch();
        startAllBids();
    }

    public void launchBid(String idBid){
        startBid(idBid);
    }

    public Winner processPrices(EncryptedPrices encryptedPrices) throws Exception {
        double price1 = 0;
        byte[] encrypted1 = null;
        double decrypted;
        for (byte[] encrypted : encryptedPrices.getPrices()) {
            decrypted = EncryptionUtil.decryptPrice(encrypted,manager.getManagerPrivateKey());
            if (decrypted > price1){
                price1 = decrypted;
                encrypted1 = encrypted;
            }
        }
        double price2 = -1;
        for (byte[] encrypted : encryptedPrices.getPrices()) {
            decrypted = EncryptionUtil.decryptPrice(encrypted,manager.getManagerPrivateKey());
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

    public IManagerUserInterface getUi() {
        return ui;
    }
}