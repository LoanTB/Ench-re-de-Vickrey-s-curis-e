package com.projetenchere.common.Models.Encrypted.DJ;

import java.security.PrivateKey;
import java.util.Arrays;

public class DJPrivateKey extends DJKey implements PrivateKey{

    private boolean isDestroyed = false;

    public DJPrivateKey(byte[] key) {
        super(key);
    }

    @Override
    public String getFormat() {
        return "X.509";
    }

    @Override
    public void destroy() {
        Arrays.fill(this.key, (byte) 0);
        this.isDestroyed = true;
    }

    @Override
    public boolean isDestroyed() {
        return this.isDestroyed;
    }
}
