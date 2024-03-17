package com.projetenchere.common.Models.SignedPack;

import java.io.Serializable;
import java.security.PublicKey;

public class SigPack_PriceWin extends AbstractSignedPack implements Serializable {

    //TODO : JavaDoc

    /**
     * Signed by Manager received by Seller
     *
     * @param price
     * @param priceSigned
     * @param signaturePubKey
     */
    public SigPack_PriceWin(double price, byte[] priceSigned, PublicKey signaturePubKey) {
        super(price, priceSigned, signaturePubKey);
    }

}
