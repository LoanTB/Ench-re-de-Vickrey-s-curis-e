package com.projetenchere.common.Models;

import java.security.PublicKey;
import java.security.Signature;

public class User {

    private Signature signature;
    private PublicKey key;

    public synchronized Signature getSignature() {
        return signature;
    }

    public void setSignature(Signature signature) {
        this.signature = signature;
    }

    public synchronized PublicKey getKey() {
        return key;
    }

    public void setKey(PublicKey key) {
        this.key = key;
    }

}