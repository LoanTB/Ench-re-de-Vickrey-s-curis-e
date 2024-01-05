package com.projetenchere.Manager.Controllers;

import com.projetenchere.Manager.Handlers.BidSetReplyer;
import com.projetenchere.Manager.Handlers.EncryptedOffersSetReplyer;
import com.projetenchere.Manager.Handlers.NewBidReplyer;
import com.projetenchere.Manager.Handlers.PubKeyReplyer;
import com.projetenchere.Manager.Model.Manager;
import com.projetenchere.Manager.View.IManagerUserInterface;
import com.projetenchere.Manager.View.commandLineInterface.ManagerCommandLineInterface;
import com.projetenchere.Manager.View.graphicalUserInterface.IManagerUserInterfaceFactory;
import com.projetenchere.Manager.View.graphicalUserInterface.ManagerGraphicalUserInterface;
import com.projetenchere.common.Models.Bid;
import com.projetenchere.common.Controllers.Controller;
import com.projetenchere.common.Models.CurrentBids;
import com.projetenchere.common.Utils.EncryptionUtil;
import com.projetenchere.common.network.Headers;
import com.projetenchere.common.network.Server;

import java.security.KeyPair;

public class ManagerController extends Controller {
    private IManagerUserInterface ui;

    public ManagerController(IManagerUserInterfaceFactory uiFactory) throws Exception {
        this.ui = uiFactory.createManagerUserInterface();
    }

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
        managerServer.addHandler(Headers.RESOLVE_BID, new EncryptedOffersSetReplyer());
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