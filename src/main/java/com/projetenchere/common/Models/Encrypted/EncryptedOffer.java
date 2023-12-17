package com.projetenchere.common.Models.Encrypted;

import com.projetenchere.common.Models.Offer;
import com.projetenchere.common.Utils.EncryptionUtil;
import com.projetenchere.common.Utils.SignatureUtil;

import java.io.Serializable;
import java.security.PublicKey;
import java.security.Signature;

public class EncryptedOffer implements Serializable {
    private byte[] priceSigned;
    private PublicKey bidderPubKey;
    private final byte[] price;

    public EncryptedOffer(Signature signature, Offer offer, PublicKey myPubKey, PublicKey managerPubKey) throws Exception {
        this.price = EncryptionUtil.encryptPrice(offer.getPrice(), managerPubKey);
        this.priceSigned = SignatureUtil.signData(price,signature);
        this.bidderPubKey = myPubKey;
    }

    public byte[] getPriceSigned() {
        return this.priceSigned;
    }

    public void hidePriceSigned() {
        this.priceSigned = null;
        this.bidderPubKey = null;
    }

    public byte[] getPrice() {
        return price;
    }

    public PublicKey getPublicKey(){
        return bidderPubKey;
    }
}