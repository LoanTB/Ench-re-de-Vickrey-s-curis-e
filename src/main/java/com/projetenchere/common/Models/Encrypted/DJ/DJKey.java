package com.projetenchere.common.Models.Encrypted.DJ;

import java.security.Key;

public abstract class DJKey implements Key {

    protected byte[] key;
    @Override
    public String getAlgorithm() {
        return "DJ";
    }
    @Override
    public byte[] getEncoded() {
        return key;
    }

    public DJKey(byte[] key) {
        this.key = key;
    }
}
