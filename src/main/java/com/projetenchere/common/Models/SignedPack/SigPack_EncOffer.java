package com.projetenchere.common.Models.SignedPack;

import java.io.Serializable;
import java.security.PublicKey;

//TODO : JavaDoc
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

    public String getBidId() {
        return bidId;
    }
}