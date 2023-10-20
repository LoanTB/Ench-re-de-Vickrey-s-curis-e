package com.projetenchere.common.Model.Encrypted;

import java.util.Set;

public class EncryptedPrices {
    private final Set<byte[]> prices;

    public EncryptedPrices(Set<byte[]> pices) {
        this.prices = pices;
    }

    public Set<byte[]> getPrices() {
        return prices;
    }
}
