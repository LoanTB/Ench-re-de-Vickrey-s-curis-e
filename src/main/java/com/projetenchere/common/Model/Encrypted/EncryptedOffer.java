package com.projetenchere.common.Model.Encrypted;

import com.projetenchere.common.Model.Offer;
import com.projetenchere.common.Util.EncryptionUtils;

import java.io.Serializable;
import java.security.PublicKey;

public class EncryptedOffer implements Serializable {
    private final String idBidder;
    private final byte[] price;

    public EncryptedOffer(String idBidder, double price, PublicKey publicKey) throws Exception {
        this.idBidder = idBidder;
        this.price = EncryptionUtils.encrypt(price,publicKey);
    }

    public EncryptedOffer(Offer offer, PublicKey publicKey) throws Exception {
        this.idBidder = offer.getIdBidder();
        this.price = EncryptionUtils.encrypt(offer.getPrice(),publicKey);
    }

    public String getIdBidder() {
        return idBidder;
    }

    public byte[] getPrice() {
        return price;
    }
}