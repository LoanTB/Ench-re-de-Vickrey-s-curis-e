package com.projetenchere.common.model.signedPack;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Set_SigPackEncOffer implements Serializable {
    private final String bidId;
    private Set<SigPack_EncOffer> offers;
    private int nbParticipant;

    public Set_SigPackEncOffer(String bidId, Set<SigPack_EncOffer> offers, int nbParticipant) {
        this.bidId = bidId;
        this.offers = offers;
        this.nbParticipant = nbParticipant;
    }

    public synchronized String getBidId() {
        return bidId;
    }

    public Set<byte[]> getPrices() {
        Set<byte[]> prices = new HashSet<>();
        for (SigPack_EncOffer offer : offers) {
            prices.add((byte[]) offer.getObject());
        }
        return prices;
    }

    public synchronized Set<SigPack_EncOffer> getOffers() {
        return offers;
    }
    public synchronized int getNbParticipant() {
        return nbParticipant;
    }
    public void setNbParticipant(int nbParticipant) {
        this.nbParticipant = nbParticipant;
    }
    public boolean contains(SigPack_EncOffer a) {
        for (SigPack_EncOffer offer : offers) {
            if (Arrays.equals(a.getObjectSigned(), offer.getObjectSigned()) && a.getBidId().equals(offer.getBidId())) {
                return true;
            }
        }
        return false;
    }

}
