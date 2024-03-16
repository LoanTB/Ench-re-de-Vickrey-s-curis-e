package com.projetenchere.common.Models.Encrypted;

import com.projetenchere.common.Utils.SignatureUtil;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class EncryptedOffersSet implements Serializable {
    private final String bidId;
    public Set<SigPack_EncOffer> offers;

    public EncryptedOffersSet(String bidId, Set<SigPack_EncOffer> offers) {
        this.bidId = bidId;
        this.offers = offers;
    }

    public synchronized String getBidId() {
        return bidId;
    }

    public Set<byte[]> getPrices() {
        Set<byte[]> prices = new HashSet<>();
        for (SigPack_EncOffer offer : offers) {
            prices.add(SignatureUtil.objectToArrayByte(offer.getObject()));
        }
        return prices;
    }

    public synchronized Set<SigPack_EncOffer> getOffers() {
        return offers;
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
