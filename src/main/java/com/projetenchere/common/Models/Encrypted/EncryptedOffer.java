package com.projetenchere.common.Models.Encrypted;

import com.projetenchere.common.Models.Offer;
import com.projetenchere.common.Utils.EncryptionUtil;

import java.io.Serializable;
import java.security.PublicKey;

public class EncryptedOffer implements Serializable {
    private final String idBidder;
    private final byte[] price;

    public EncryptedOffer(String idBidder, double price, PublicKey publicKey) throws Exception {
        this.idBidder = idBidder;
        this.price = EncryptionUtil.encryptPrice(price,publicKey);
    }

    public EncryptedOffer(Offer offer, PublicKey publicKey) throws Exception {
        this.idBidder = offer.getIdBidder();
        this.price = EncryptionUtil.encryptPrice(offer.getPrice(),publicKey);
    }

    public String getIdBidder() {
        return idBidder;
    }

    public byte[] getPrice() {
        return price;
    }
}