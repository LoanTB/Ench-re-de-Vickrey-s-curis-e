package com.projetenchere.Manager.Controllers;

import com.projetenchere.Manager.Handlers.BidSetReplyer;
import com.projetenchere.Manager.Handlers.EncOffersProductReplyer;
import com.projetenchere.Manager.Handlers.NewBidReplyer;
import com.projetenchere.Manager.Handlers.PubKeyReplyer;
import com.projetenchere.Manager.Model.Manager;
import com.projetenchere.Manager.View.graphicalUserInterface.ManagerGraphicalUserInterface;
import com.projetenchere.common.Controllers.Controller;
import com.projetenchere.common.Utils.EncryptionUtil;
import com.projetenchere.common.network.Headers;
import com.projetenchere.common.network.Server;

import java.security.KeyPair;

public class ManagerController extends Controller {
    private final Manager manager = Manager.getInstance();
    ManagerGraphicalUserInterface ui;

    public ManagerController(ManagerGraphicalUserInterface ui) {
        this.ui = ui;
    }

    public void setSignatureConfig() throws Exception {
        setSignatureConfig(ui, manager,"manager"); //TODO : A retirer à la fin du projet.
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
        managerServer.addHandler(Headers.RESOLVE_BID, new EncOffersProductReplyer());
        managerServer.start();
        ui.tellManagerReadyToProcessBids();
    }

    public void displayHello() {
        ui.displayHello();
    }

}