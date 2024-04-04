package com.projetenchere.manager.controller;

import com.projetenchere.common.controller.Controller;
import com.projetenchere.common.model.signedPack.SigPack_PubKey;
import com.projetenchere.common.network.Headers;
import com.projetenchere.common.network.Server;
import com.projetenchere.common.util.EncryptionUtil;
import com.projetenchere.common.util.SignatureUtil;
import com.projetenchere.manager.model.Manager;
import com.projetenchere.manager.network.handler.BidSetReplyer;
import com.projetenchere.manager.network.handler.EncOffersProductReplyer;
import com.projetenchere.manager.network.handler.NewBidReplyer;
import com.projetenchere.manager.network.handler.PubKeyReplyer;
import com.projetenchere.manager.view.IManagerUserInterface;

import java.security.KeyPair;
import java.security.SignatureException;

public class ManagerController extends Controller {
    private final Manager manager = Manager.getInstance();
    IManagerUserInterface ui;

    public ManagerController(IManagerUserInterface ui) {
        this.ui = ui;
    }

    public void setSignatureConfig() throws Exception {
        setSignatureConfig(ui, manager,"manager");
    }

    public void init() throws SignatureException {
        Server managerServer = new Server(24683);
        Manager manager = Manager.getInstance();
        ui.tellKeysGeneration();
        KeyPair keys = EncryptionUtil.generateKeyPair();
        manager.setPrivateKey(keys.getPrivate());
        manager.setPublicKey(keys.getPublic());

        byte[] signedPub = SignatureUtil.signData(manager.getPublicKey(),manager.getSignature());
        SigPack_PubKey sigPackPubKey = new SigPack_PubKey(manager.getPublicKey(),signedPub,manager.getKey());
        manager.setPublicKeySigned(sigPackPubKey);
        ui.tellKeysReady();

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