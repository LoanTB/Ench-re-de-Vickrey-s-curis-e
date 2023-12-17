package com.projetenchere.common.Models.Encrypted;

import com.projetenchere.common.Models.Offer;
import com.projetenchere.common.Utils.EncryptionUtil;

import java.io.Serializable;
import java.security.PublicKey;
import java.security.Signature;

public class EncryptedOffer implements Serializable {
    private Signature signature;
    private PublicKey bidderPubKey;
    private final byte[] price;

    public EncryptedOffer(Signature signature, Offer offer, PublicKey myPubKey, PublicKey managerPubKey) throws Exception {
        this.signature = signature;
        this.price = EncryptionUtil.encryptPrice(offer.getPrice(), managerPubKey);
        this.bidderPubKey = myPubKey;
    }

    public Signature getSignature() {
        return this.signature;
    }

    public void hideSignature() {
        this.signature = null;
        this.bidderPubKey = null;
    }

    public byte[] getPrice() {
        return price;
    }
}