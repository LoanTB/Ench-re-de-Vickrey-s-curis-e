package com.projetenchere.common.Models.Encrypted;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class EncryptedOffersSet implements Serializable {
    private final String bidId;
    public Set<EncryptedOffer> offers;

    public EncryptedOffersSet(String bidId, Set<EncryptedOffer> offers) {
        this.bidId = bidId;
        this.offers = offers;
    }

    public synchronized String getBidId() {
        return bidId;
    }

    public Set<byte[]> getPrices() {
        Set<byte[]> prices = new HashSet<>();
        for (EncryptedOffer offer : offers) {
            prices.add(offer.getPrice());
        }
        return prices;
    }

    public synchronized Set<EncryptedOffer> getOffers() {
        return offers;
    }

    public boolean contains(EncryptedOffer a) {
        for (EncryptedOffer offer : offers) {
            if (Arrays.equals(a.getPriceSigned(), offer.getPriceSigned()) && a.getBidId().equals(offer.getBidId())) {
                return true;
            }
        }
        return false;
    }

}
