package com.projetenchere.common.controller;

import com.projetenchere.common.model.User;
import com.projetenchere.common.util.SignatureUtil;
import com.projetenchere.common.util.keyFile.I_KeyFileUtil;
import com.projetenchere.common.util.keyFile.KeyFileUtilWithJKS;
import com.projetenchere.common.view.IUserInterface;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

public abstract class Controller {

    public void setSignatureConfig(IUserInterface ui, User user, String name) throws Exception {
        ui.tellSignatureConfigSetup();
        I_KeyFileUtil keyFile = new KeyFileUtilWithJKS("_"+name);

        if (!keyFile.isKeyPairSaved()) {
            ui.tellSignatureConfigGeneration();
            keyFile.generateAndSaveKeyPair();
        }

        PublicKey publicKey = keyFile.getPublicKeyFromFile();
        PrivateKey privateKey = keyFile.getPrivateKeyFromFile();
        Signature signature = SignatureUtil.initSignatureForSigning(privateKey);

        user.setSignature(signature);
        user.setKey(publicKey);
        ui.tellSignatureConfigReady();
    }

    public void waitSynchro(int ms) {
        synchronized (this) {
            try {
                wait(ms);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
