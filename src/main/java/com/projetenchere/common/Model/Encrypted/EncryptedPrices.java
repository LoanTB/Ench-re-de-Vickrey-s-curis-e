package com.projetenchere.common.Model.Encrypted;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class EncryptedPrices implements Serializable {
    private final Set<byte[]> prices;

    public EncryptedPrices(Set<byte[]> prices) {
        this.prices = prices;
    }

    public Set<byte[]> getPrices() {
        return prices;
    }
}
