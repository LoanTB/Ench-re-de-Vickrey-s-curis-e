package com.projetenchere.Manager.Controller;

import com.projetenchere.Manager.Model.Manager;
import com.projetenchere.Manager.View.IManagerUserInterface;
import com.projetenchere.Manager.View.commandLineInterface.ManagerCommandLineInterface;
import com.projetenchere.common.Model.Bid;
import com.projetenchere.common.Model.BidStarter;
import com.projetenchere.common.Model.Encrypted.EncryptedPrices;
import com.projetenchere.common.Model.Winner;
import com.projetenchere.common.Utils.EncryptionUtil;

import java.io.IOException;
import java.net.UnknownHostException;

public class ManagerController {
    public final IManagerUserInterface ui = new ManagerCommandLineInterface();
    public final ManagerNetworkController networkController = new ManagerNetworkController();
    public final Manager manager = new Manager();

    public ManagerController() throws UnknownHostException {
    }

    private Bid createBid() {
        return ui.askBidInformations();
    }

    private void askSellerAddress() {
        networkController.setSellerAddress(ui.askSellerAddress());
    }

    public Bid initBid() throws IOException {
        Bid currentBid = createBid();
        System.out.println("Vous avez créé l'enchère : ");
        System.out.println(currentBid._toString());
        askSellerAddress();
        networkController.sendBidToSeller(currentBid);
        return currentBid;
    }

    public void generateManagerKeys() throws Exception {
        manager.setManagerKeys(EncryptionUtil.generateKeyPair());
    }

    public void launchBid(Bid currentBid) throws IOException, ClassNotFoundException, InterruptedException {
        BidStarter currentBidStarter = new BidStarter(manager.getManagerPublicKey(), currentBid, networkController.getSellerAddress());
        networkController.waitAskInitPackByBidder(currentBidStarter);
    }

    public EncryptedPrices waitEncryptedPrices() throws IOException, ClassNotFoundException {
        return networkController.fetchEncryptedPrice();
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
        return new Winner(encrypted1,price2);
    }

    public void showWinnerPrice(Winner winner) {
        ui.displayWinnerPrice(winner);
    }

    public void endBid(Winner win) throws IOException {
        networkController.sendWinnerAndPrice(win);
    }
}