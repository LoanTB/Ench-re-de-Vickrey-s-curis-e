package com.projetenchere.common.Models.Encrypted.DJ;

import java.security.PublicKey;

public class DJPublicKey extends DJKey implements PublicKey {

    public DJPublicKey(byte[] key) {
        super(key);
    }

    @Override
    public String getFormat() {
        return "PKCS#8";
    }
}
