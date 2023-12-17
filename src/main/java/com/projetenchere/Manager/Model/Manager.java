package com.projetenchere.Manager.Model;

import com.projetenchere.common.Models.User;

import java.net.InetSocketAddress;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class Manager extends User {
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