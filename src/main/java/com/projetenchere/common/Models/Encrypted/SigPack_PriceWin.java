package com.projetenchere.common.Models.Encrypted;

import com.projetenchere.common.Utils.SignatureUtil;

import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.security.Signature;

public class SigPack_PriceWin extends AbstractSignedPack implements Serializable {

    //TODO : JavaDoc
    /**
     * Signed by Manager received by Seller
     * @param price
     * @param priceSigned
     * @param signaturePubKey
     */
    public SigPack_PriceWin(double price, byte[] priceSigned, PublicKey signaturePubKey) {
        super(price,priceSigned,signaturePubKey);
    }

}
