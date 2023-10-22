package com.projetenchere.Manager.Controller;

import com.projetenchere.Manager.Model.Manager;
import com.projetenchere.Manager.View.IManagerUserInterface;
import com.projetenchere.Manager.View.commandLineInterface.ManagerCommandLineInterface;
import com.projetenchere.common.Model.Bid;
import com.projetenchere.common.Model.BidStarter;
import com.projetenchere.common.Model.Encrypted.EncryptedPrices;
import com.projetenchere.common.Model.Winner;
import com.projetenchere.common.Util.EncryptionUtil;

import java.io.IOException;
import java.net.UnknownHostException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashSet;
import java.util.Set;

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

    public Set<Double> decryptEncryptedPrice(EncryptedPrices receivedPrices, PrivateKey managerPrivateKey) throws Exception {
        Set<Double> decryptedPrice = new HashSet<>();
        for (byte[] encrypted : receivedPrices.getPrices()) {
            decryptedPrice.add(EncryptionUtil.decrypt(encrypted, managerPrivateKey));
        }
        return decryptedPrice;
    }

    public void showPrices(Set<Double> decryptedPrice) {
        ui.displayPrices(decryptedPrice);
    }

    public Winner getWinnerPrice(PublicKey managerKey, Set<Double> prices) throws Exception {

        double firstPrice = 0;
        double secondPrice = 0;

        for (Double priceProcess : prices) {
            if (priceProcess > secondPrice) {
                if (priceProcess > firstPrice) {
                    secondPrice = firstPrice;
                    firstPrice = priceProcess;
                } else {
                    secondPrice = priceProcess;
                }
            }
        }
        if(secondPrice == 0){
            secondPrice = firstPrice;
        }
        byte[] winnerCypher = EncryptionUtil.encrypt(secondPrice, managerKey);

        return new Winner(winnerCypher, firstPrice);
    }
    public Winner processPrices(EncryptedPrices encrypted) throws Exception {
        Set<Double> currentDecryptedPrices = decryptEncryptedPrice(encrypted, manager.getManagerPrivateKey());
        showPrices(currentDecryptedPrices);

       Winner win = getWinnerPrice(manager.getManagerPublicKey(), currentDecryptedPrices);
        return win;
    }
    public void showWinnerPrice(Winner winner) {
        ui.displayWinnerPrice(winner);
    }
    public void endBid(Winner win) throws IOException {
        networkController.sendWinnerAndPrice(win);
    }
}