package com.projetenchere.common.Models.Encrypted;

import java.io.Serializable;
import java.security.PublicKey;

//TODO : JavaDoc
public class EncryptedOffer extends AbstractSignedPack implements Serializable {
    private final String bidId;

    /**
     * @param encPrice
     * @param encPriceSigned
     * @param bidderSignaturePubKey
     * @param bidId
     */
    public EncryptedOffer(byte[] encPrice, byte[] encPriceSigned, PublicKey bidderSignaturePubKey, String bidId) {
        super(encPrice, encPriceSigned, bidderSignaturePubKey);
        this.bidId = bidId;
    }

    public String getBidId() {
        return bidId;
    }
}