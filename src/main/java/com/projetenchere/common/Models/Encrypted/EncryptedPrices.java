package com.projetenchere.common.Models.Encrypted;

import java.io.Serializable;
import java.util.Set;

public class EncryptedPrices implements Serializable {

    private final int bidId;
    private final Set<byte[]> prices;

    public EncryptedPrices(int bidId,Set<byte[]> prices) {
        this.bidId = bidId;
        this.prices = prices;
    }

    public int getBidId() {
        return bidId;
    }

    public Set<byte[]> getPrices() {
        return prices;
    }
}
