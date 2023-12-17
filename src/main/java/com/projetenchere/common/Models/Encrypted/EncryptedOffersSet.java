package com.projetenchere.common.Models.Encrypted;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class EncryptedOffersSet implements Serializable {
    public Set<EncryptedOffer> offers;
    private final String bidId;

    public EncryptedOffersSet(String bidId,Set<EncryptedOffer> offers) {
        this.bidId = bidId;
        this.offers = offers;
    }

    public String getBidId() {
        return bidId;
    }

    public Set<byte[]> getPrices() {
        Set<byte[]> prices = new HashSet<>();
        for(EncryptedOffer offer : offers){
            prices.add(offer.getPrice());
        }
        return prices;
    }

    public Set<EncryptedOffer> getOffers(){
        return offers;
    }

}
