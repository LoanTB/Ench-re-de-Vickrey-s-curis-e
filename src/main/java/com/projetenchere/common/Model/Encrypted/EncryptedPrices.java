package com.projetenchere.common.Model.Encrypted;

import java.util.Set;

public class EncryptedPrices {
    private final Set<byte[]> pices;

    public EncryptedPrices(Set<byte[]> pices) {
        this.pices = pices;
    }

    public Set<byte[]> getPices() {
        return pices;
    }
}
