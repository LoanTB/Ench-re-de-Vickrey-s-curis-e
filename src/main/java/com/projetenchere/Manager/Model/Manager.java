package com.projetenchere.Manager.Model;

import com.projetenchere.Manager.Controller.ManagerController;

import java.io.*;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.*;

public class Manager {
    private KeyPair ManagerKeys;
    private PrivateKey managerPrivateKey;
    private PublicKey managerPublicKey;
    private String sellerAddress;

    public KeyPair getManagerKeys() {
        return ManagerKeys;
    }

    public void setManagerKeys(KeyPair managerKeys) {
        ManagerKeys = managerKeys;
        managerPrivateKey = ManagerKeys.getPrivate();
        managerPublicKey = ManagerKeys.getPublic();
    }

    public PrivateKey getManagerPrivateKey() {
        return managerPrivateKey;
    }

    public PublicKey getManagerPublicKey() {
        return managerPublicKey;
    }

    public String getSellerAddress() {
        return sellerAddress;
    }

    public void setSellerAddress(String sellerAddress) {
        this.sellerAddress = sellerAddress;
    }

}