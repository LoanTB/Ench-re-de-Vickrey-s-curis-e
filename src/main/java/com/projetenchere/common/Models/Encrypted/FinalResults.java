package com.projetenchere.common.Models.Encrypted;

import com.projetenchere.common.Utils.SignatureUtil;

import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.security.Signature;

public class FinalResults implements Serializable {
    private final byte[] winnerPriceSignedByAutority;
    private final PublicKey signaturePubKeyAutority;
    private final byte[] winnerPriceSignedBySeller;
    private final PublicKey signaturePubKeySeller;
    private final double winnerPrice;

    public FinalResults(Signature signatureSeller, PublicKey signaturePubKeySeller,
                        PublicKey signaturePubKeyAutority, byte[] winnerPriceSignedByAutority, double price) throws GeneralSecurityException {
        this.winnerPrice = price;
        this.signaturePubKeyAutority = signaturePubKeyAutority;
        this.winnerPriceSignedByAutority = winnerPriceSignedByAutority;
        this.winnerPriceSignedBySeller = SignatureUtil.signData(price, signatureSeller);
        this.signaturePubKeySeller = signaturePubKeySeller;
    }

    public byte[] getWinnerPriceSignedByAutority() {
        return winnerPriceSignedByAutority;
    }

    public PublicKey getSignaturePubKeyAutority() {
        return signaturePubKeyAutority;
    }

    public byte[] getWinnerPriceSignedBySeller() {
        return winnerPriceSignedBySeller;
    }

    public PublicKey getSignaturePubKeySeller() {
        return signaturePubKeySeller;
    }

    public double getWinnerPrice() {
        return winnerPrice;
    }
}
