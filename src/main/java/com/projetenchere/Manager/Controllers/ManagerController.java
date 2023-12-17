package com.projetenchere.Manager.Controllers;

import com.projetenchere.Manager.Handlers.BidSetReplyer;
import com.projetenchere.Manager.Handlers.EncryptedPricesReplyer;
import com.projetenchere.Manager.Handlers.NewBidReplyer;
import com.projetenchere.Manager.Handlers.PubKeyReplyer;
import com.projetenchere.Manager.Model.Manager;
import com.projetenchere.Manager.View.IManagerUserInterface;
import com.projetenchere.Manager.View.commandLineInterface.ManagerCommandLineInterface;
import com.projetenchere.common.Models.Bid;
import com.projetenchere.common.Controllers.Controller;
import com.projetenchere.common.Models.Encrypted.EncryptedPrices;
import com.projetenchere.common.Models.CurrentBids;
import com.projetenchere.common.Models.Winner;
import com.projetenchere.common.Utils.EncryptionUtil;
import com.projetenchere.common.network.Headers;
import com.projetenchere.common.network.Server;

import java.security.KeyPair;
import java.security.PrivateKey;

public class ManagerController extends Controller {
    public final IManagerUserInterface ui = new ManagerCommandLineInterface();

    private final CurrentBids currentBids = new CurrentBids();

    private final Manager manager = Manager.getInstance();

    public ManagerController() throws Exception {}

    public void setSignatureConfig() throws Exception {
        setSignatureConfig(ui,manager);
    }

    public CurrentBids getCurrentBids() {
        return currentBids;
    }

    public void addBid(Bid bid) throws Exception {
        currentBids.addCurrentBid(bid);
    }

    public void startAllBids(){
        currentBids.startAllBids();
    }

    public void startBid(String idBid) {
        currentBids.startBids(idBid);
    }

    public void init() {
        Server managerServer = new Server(24683);
        Manager manager = Manager.getInstance();
        KeyPair keys = EncryptionUtil.generateKeyPair();
        manager.setPrivateKey(keys.getPrivate());
        manager.setPublicKey(keys.getPublic());
        managerServer.addHandler(Headers.GET_PUB_KEY, new PubKeyReplyer());
        managerServer.addHandler(Headers.GET_CURRENT_BIDS, new BidSetReplyer());
        managerServer.addHandler(Headers.NEW_BID, new NewBidReplyer());
        managerServer.addHandler(Headers.RESOLVE_BID, new EncryptedPricesReplyer());
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

    public void displayHello(){
        ui.displayHello();
    }
}