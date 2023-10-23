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
import java.util.ArrayList;
import java.util.List;

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

    public List<Double> decryptEncryptedPrice(EncryptedPrices receivedPrices, PrivateKey managerPrivateKey) throws Exception {
        List<Double> decryptedPrice = new ArrayList<>();
        for (byte[] encrypted : receivedPrices.getPrices()) {
            decryptedPrice.add(EncryptionUtil.decrypt(encrypted, managerPrivateKey));
        }
        return decryptedPrice;
    }

    public void showPrices(List<Double> decryptedPrice) {
        ui.displayPrices(decryptedPrice);
    }

    public Winner getWinnerPrice(EncryptedPrices encryptedPrices, List<Double> prices) throws Exception {

        Double firstPrice = 0.0;
        Double secondPrice = 0.0;

        int indexMax = 0;
        for (int i = 0 ; i<prices.size();i++){
            Double priceProcess = prices.get(i);
            if (priceProcess > secondPrice) {
                if (priceProcess > firstPrice) {
                    secondPrice = firstPrice;
                    firstPrice = priceProcess;
                    indexMax = i;
                } else {
                    secondPrice = priceProcess;
                }
            }
        }
        if (secondPrice.equals(0.0)) {
            secondPrice = firstPrice;
        }

        byte[] winnerCypher = encryptedPrices.getPrices().get(indexMax);

        return new Winner(winnerCypher, secondPrice);
    }

    public Winner processPrices(EncryptedPrices encrypted) throws Exception {
        List<Double> currentDecryptedPrices = decryptEncryptedPrice(encrypted, manager.getManagerPrivateKey());
        showPrices(currentDecryptedPrices);

        Winner win = getWinnerPrice(encrypted, currentDecryptedPrices);
        return win;
    }

    public void showWinnerPrice(Winner winner) {
        ui.displayWinnerPrice(winner);
    }

    public void endBid(Winner win) throws IOException {
        networkController.sendWinnerAndPrice(win);
    }
}