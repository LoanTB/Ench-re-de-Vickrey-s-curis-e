package com.projetenchere.common.Models.Encrypted;

import com.projetenchere.common.Utils.SignatureUtil;

import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.security.Signature;

public class WinnerSignedAutority implements Serializable {
    private final byte[] winnerPriceSigned;
    private final PublicKey signaturePubKey;
    private final double price;

    public WinnerSignedAutority(Signature signature, PublicKey signaturePubKey, double price, EncryptedOffersSet list) throws GeneralSecurityException {
        this.price = price;
        this.winnerPriceSigned = SignatureUtil.signData(price, signature);
        this.signaturePubKey = signaturePubKey;
    }

    public byte[] getWinnerPriceSigned() {
        return winnerPriceSigned;
    }

    public double getPrice() {
        return price;
    }

    public PublicKey getSignaturePubKey() {
        return signaturePubKey;
    }


}
