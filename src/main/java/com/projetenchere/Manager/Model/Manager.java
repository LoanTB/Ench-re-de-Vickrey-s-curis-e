package com.projetenchere.Manager.Model;

import com.projetenchere.common.Model.Bid;
import com.projetenchere.common.Model.Encrypted.EncryptedPrices;
import com.projetenchere.common.Model.Winner;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class Manager {
    private PrivateKey managerPrivateKey;
    private PublicKey managerPublicKey;

    public void setManagerKeys(KeyPair managerKeys) {
        managerPrivateKey = managerKeys.getPrivate();
        managerPublicKey = managerKeys.getPublic();
    }

    public PrivateKey getManagerPrivateKey() {
        return managerPrivateKey;
    }

    public PublicKey getManagerPublicKey() {
        return managerPublicKey;
    }



}