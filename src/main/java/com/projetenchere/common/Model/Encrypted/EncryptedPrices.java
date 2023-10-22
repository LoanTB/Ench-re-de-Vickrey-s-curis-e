package com.projetenchere.common.Model.Encrypted;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EncryptedPrices implements Serializable {
    private final List<byte[]> prices;

    public EncryptedPrices(ArrayList<byte[]> prices) {
        this.prices = prices;
    }

    public List<byte[]> getPrices() {
        return prices;
    }
}
