package com.projetenchere.common.Controllers;

import com.projetenchere.common.Models.User;
import com.projetenchere.common.Utils.I_KeyFileUtil;
import com.projetenchere.common.Utils.KeyFileUtilWithJKS;
import com.projetenchere.common.Utils.SignatureUtil;
import com.projetenchere.common.View.I_UserInterface;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

public abstract class Controller {

    public void setSignatureConfig(I_UserInterface ui, User user, String name) throws Exception { //TODO : name A retirer  à la fin du projet.
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
