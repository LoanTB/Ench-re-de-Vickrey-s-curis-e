package com.projetenchere.common.Models.Encrypted;

import java.io.Serializable;
import java.util.Set;

public class EncryptedPrices implements Serializable {

    private final String bidId;
    private final Set<byte[]> prices;

    public EncryptedPrices(String bidId, Set<byte[]> prices) {
        this.bidId = bidId;
        this.prices = prices;
    }
//TODO : Choisir entre encryptedPrice et encryptedOffer.

    public String getBidId() {
        return bidId;
    }

    public Set<byte[]> getPrices() {
        return prices;
    }
}
