package com.projetenchere.Manager.Model;

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