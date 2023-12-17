package com.projetenchere.Manager.Controllers;

import com.projetenchere.Manager.Handlers.BidSetReplyer;
import com.projetenchere.Manager.Handlers.PubKeyReplyer;
import com.projetenchere.Manager.View.IManagerUserInterface;
import com.projetenchere.Manager.View.commandLineInterface.ManagerCommandLineInterface;
import com.projetenchere.common.Models.Bid;
import com.projetenchere.common.Controllers.Controller;
import com.projetenchere.common.Models.Encrypted.EncryptedPrices;
import com.projetenchere.common.Models.Network.Communication.CurrentBids;
import com.projetenchere.common.Models.Network.Communication.CurrentBidsPrivateKeys;
import com.projetenchere.common.Models.Network.Communication.CurrentBidsPublicKeys;
import com.projetenchere.common.Models.Network.Communication.Winner;
import com.projetenchere.common.Utils.EncryptionUtil;
import com.projetenchere.common.network.Headers;
import com.projetenchere.common.network.Server;

import java.security.PrivateKey;

public class ManagerController extends Controller {
    public final IManagerUserInterface ui = new ManagerCommandLineInterface();

    private final CurrentBids currentBids = new CurrentBids();
    private final CurrentBidsPublicKeys currentBidsPublicKeys = new CurrentBidsPublicKeys();
    private final CurrentBidsPrivateKeys currentBidsPrivateKeys = new CurrentBidsPrivateKeys();

    public ManagerController() throws Exception {}

    public CurrentBids getCurrentBids() {
        return currentBids;
    }

    public void addBid(Bid bid) throws Exception {
        currentBids.addCurrentBid(bid);
        currentBidsPrivateKeys.addKeyToBid(bid.getId(),EncryptionUtil.generateKeyPair());
        currentBidsPublicKeys.addKeyToBid(bid.getId(),currentBidsPrivateKeys.getKeyOfBid(bid.getId()).getPublic());
    }

    public CurrentBidsPublicKeys getCurrentBidsPublicKeys() {
        return currentBidsPublicKeys;
    }

    public CurrentBidsPrivateKeys getCurrentBidsPrivateKeys() {
        return currentBidsPrivateKeys;
    }

    public void startAllBids(){
        currentBids.startAllBids();
    }

    public void startBid(String idBid) {
        currentBids.startBids(idBid);
    }

    public void init() {
        Server managerServer = new Server();
        managerServer.addHandler(Headers.GET_PUB_KEY, new PubKeyReplyer());
        managerServer.addHandler(Headers.GET_CURRENT_BIDS, new BidSetReplyer());
        managerServer.start();
        ui.tellManagerReadyToProcessBids();
    }

    public void launchBids(){
        ui.displayBidLaunch();
        startAllBids();
    }

    public void launchBid(String idBid){
        startBid(idBid);
    }

    public Winner processPrices(EncryptedPrices encryptedPrices, PrivateKey privateKey) throws Exception {
        double price1 = 0;
        byte[] encrypted1 = null;
        double decrypted;
        for (byte[] encrypted : encryptedPrices.getPrices()) {
            decrypted = EncryptionUtil.decryptPrice(encrypted,privateKey);
            if (decrypted > price1){
                price1 = decrypted;
                encrypted1 = encrypted;
            }
        }
        double price2 = -1;
        for (byte[] encrypted : encryptedPrices.getPrices()) {
            decrypted = EncryptionUtil.decryptPrice(encrypted,privateKey);
            if (decrypted > price2 && decrypted != price1){
                price2 = decrypted;
            }
        }
        if (price2 == -1){
            price2 = price1;
        }
        Winner winner = new Winner(encryptedPrices.getBidId(), encrypted1,price2);
        ui.displayWinnerPrice(winner);
        return winner;
    }

    public void displayHello(){
        ui.displayHello();
    }
}