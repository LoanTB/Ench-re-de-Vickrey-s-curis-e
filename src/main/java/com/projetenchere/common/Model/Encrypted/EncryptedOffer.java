package com.projetenchere.common.Model.Encrypted;

import com.projetenchere.common.Model.Offer;
import com.projetenchere.common.Util.EncryptionUtil;

import java.io.Serializable;
import java.security.PublicKey;

public class EncryptedOffer implements Serializable {
    private final String idBidder;
    private final byte[] value;

    public EncryptedOffer(String idBidder, float value, PublicKey publicKey) throws Exception {
        this.idBidder = idBidder;
        this.value = EncryptionUtil.encrypt(value,publicKey);
    }

    public EncryptedOffer(Offer offer, PublicKey publicKey) throws Exception {
        this.idBidder = offer.getIdBidder();
        this.value = EncryptionUtil.encrypt(offer.getValue(),publicKey);
    }
}