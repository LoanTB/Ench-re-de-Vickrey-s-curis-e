package com.projetenchere.common.Model.Encrypted;

import com.projetenchere.common.Model.Offer;
import com.projetenchere.common.Util.EncryptionUtil;

import java.security.PublicKey;

public class EncryptedOffer {
    private final String idBidder;
    private final byte[] price;

    public EncryptedOffer(String idBidder, double price, PublicKey publicKey) throws Exception {
        this.idBidder = idBidder;
        this.price = EncryptionUtil.encrypt(price,publicKey);
    }

    public EncryptedOffer(Offer offer, PublicKey publicKey) throws Exception {
        this.idBidder = offer.getIdBidder();
        this.price = EncryptionUtil.encrypt(offer.getPrice(),publicKey);
    }

    public String getIdBidder() {
        return idBidder;
    }

    public byte[] getPrice() {
        return price;
    }
}