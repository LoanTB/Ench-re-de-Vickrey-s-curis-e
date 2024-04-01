package com.projetenchere.common.Models.SignedPack;

import java.io.Serializable;
import java.security.PublicKey;
//TODO S2 : Refactor en ajoutant bidId dans abstractSignedPack OU Un nouveau Abstract.
public class SigPack_PriceWin extends AbstractSignedPack implements Serializable {
    private final byte[] encrypedPriceOrigin;
    private final String bidId;
    /**
     * Signed by Manager received by Seller
     *
     * @param price
     * @param priceSigned
     * @param signaturePubKey
     */
    public SigPack_PriceWin(double price, byte[] priceSigned, PublicKey signaturePubKey, byte[] encryptedPriceOrigin, String bidId) {
        super(price, priceSigned, signaturePubKey);
        this.encrypedPriceOrigin = encryptedPriceOrigin;
        this.bidId = bidId;
    }

    public byte[] getEncrypedPriceOrigin() {
        return encrypedPriceOrigin;
    }
    public String getBidId() {
        return bidId;
    }
}
