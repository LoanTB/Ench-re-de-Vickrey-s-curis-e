package com.projetenchere.common.Models.Encrypted;

import com.projetenchere.common.Models.Offer;
import com.projetenchere.common.Utils.EncryptionUtil;

import java.io.Serializable;
import java.security.PublicKey;

public class EncryptedOffer implements Serializable {
    private final String idBidder;
    private final String idBid;
    private final byte[] price;

    public EncryptedOffer(Offer offer, PublicKey publicKey) throws Exception {
        this.idBidder = offer.getIdBidder();
        this.idBid = offer.getIdBid();
        this.price = EncryptionUtil.encryptPrice(offer.getPrice(),publicKey);
    }

    public String getIdBidder() {
        return idBidder;
    }

    public String getIdBid() {
        return idBid;
    }

    public byte[] getPrice() {
        return price;
    }
}