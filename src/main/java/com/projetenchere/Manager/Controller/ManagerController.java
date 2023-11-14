package com.projetenchere.Manager.Controller;

import com.projetenchere.Manager.Controller.Network.ManagerNetworkController;
import com.projetenchere.Manager.Model.Manager;
import com.projetenchere.Manager.View.IManagerUserInterface;
import com.projetenchere.Manager.View.commandLineInterface.ManagerCommandLineInterface;
import com.projetenchere.common.Models.Bid;
import com.projetenchere.common.Models.Encrypted.EncryptedPrices;
import com.projetenchere.common.Models.Network.Communication.Winner;
import com.projetenchere.common.Utils.EncryptionUtil;

import java.io.IOException;
import java.net.UnknownHostException;

public class ManagerController {
    public final IManagerUserInterface ui = new ManagerCommandLineInterface();
    public final ManagerNetworkController networkController = new ManagerNetworkController(this);
    public final Manager manager = new Manager();

    public ManagerController() throws UnknownHostException {}

    private Bid createBid() {
        return ui.askBidInformations();
    }

    public Bid initBid() throws IOException {
        Bid bid = createBid();
        System.out.println("Vous avez créé l'enchère : ");
        System.out.println(bid._toString());
        networkController.addBid(bid);
        networkController.sendBidToSeller(bid);
        return bid;
    }

    public void generateManagerKeys() throws Exception {
        manager.setManagerKeys(EncryptionUtil.generateKeyPair());
        networkController.savePublicKey(manager.getManagerPublicKey());
    }

    public void launchBids() throws IOException, ClassNotFoundException, InterruptedException {
        networkController.startAllBids();
        networkController.startListening(networkController.getManagerPort());
    }

    public void launchBid(int id) throws IOException, ClassNotFoundException, InterruptedException {
        networkController.startBid(id);
        networkController.startListening(networkController.getManagerPort());
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
        Winner winner = new Winner(encrypted1,price2);
        showWinnerPrice(winner);
        return winner;
    }

    public void showWinnerPrice(Winner winner) {
        ui.displayWinnerPrice(winner);
    }
}