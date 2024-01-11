package com.projetenchere.common.Models.Encrypted;

import com.projetenchere.common.Models.Offer;
import com.projetenchere.common.Utils.EncryptionUtil;
import com.projetenchere.common.Utils.SignatureUtil;

import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Arrays;
import java.util.Objects;

public class EncryptedOffer implements Serializable {
    private final String bidId;
    private byte[] priceSigned;
    private PublicKey SignaturePubKey;
    private final byte[] price;

    public EncryptedOffer(Signature signature, Offer offer, PublicKey SignaturePubKey, PublicKey managerPubKey, String bidId) throws GeneralSecurityException {
        this.price = EncryptionUtil.encryptPrice(offer.getPrice(), managerPubKey);
        this.priceSigned = SignatureUtil.signData(price,signature);
        this.SignaturePubKey = SignaturePubKey;
        this.bidId = bidId;
    }

    public EncryptedOffer(Signature signature, byte[] price,PublicKey SignaturePubKey, String bidId) throws Exception {
        this.price = price;
        this.priceSigned = SignatureUtil.signData(price,signature);
        this.SignaturePubKey = SignaturePubKey;
        this.bidId = bidId;
    }

    public byte[] getPriceSigned() {
        return this.priceSigned;
    }
    public void hidePriceSigned() {
        this.priceSigned = null;
        this.SignaturePubKey = null;
    }
    public String getBidId() {
        return bidId;
    }
    public byte[] getPrice() {
        return price;
    }
    public PublicKey getSignaturePublicKey(){
        return SignaturePubKey;
    }

}