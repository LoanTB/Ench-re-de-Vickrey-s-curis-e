package com.projetenchere.common.model.signedPack;

import java.io.Serializable;
import java.security.PublicKey;

public class SigPack_EncOffer extends AbstractSignedPack implements Serializable {
    private final String bidId;

    /**
     * @param encPrice
     * @param encPriceSigned
     * @param bidderSignaturePubKey
     * @param bidId
     */
    public SigPack_EncOffer(byte[] encPrice, byte[] encPriceSigned, PublicKey bidderSignaturePubKey, String bidId) {
        super(encPrice, encPriceSigned, bidderSignaturePubKey);
        this.bidId = bidId;
    }

    public synchronized String getBidId() {
        return bidId;
    }
}