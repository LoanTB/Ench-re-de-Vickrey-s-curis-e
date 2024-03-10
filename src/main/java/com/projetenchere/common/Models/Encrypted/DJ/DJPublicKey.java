package com.projetenchere.common.Models.Encrypted.DJ;

import java.security.PublicKey;

public class DJPublicKey extends DJKey implements PublicKey {

    @Override
    public String getFormat() {
        return "PKCS#8";
    }
}
