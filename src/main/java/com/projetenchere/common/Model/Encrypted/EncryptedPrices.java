package com.projetenchere.common.Model.Encrypted;

import java.io.Serializable;
import java.util.Set;

public class EncryptedPrices implements Serializable {
    private final Set<byte[]> prices;

    public EncryptedPrices(Set<byte[]> pices) {
        this.prices = pices;
    }

    public Set<byte[]> getPrices() {
        return prices;
    }
}
