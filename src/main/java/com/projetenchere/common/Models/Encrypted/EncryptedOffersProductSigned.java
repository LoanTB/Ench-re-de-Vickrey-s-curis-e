package com.projetenchere.common.Models.Encrypted;

import com.projetenchere.common.Utils.SignatureUtil;

import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.security.Signature;

public class EncryptedOffersProductSigned implements Serializable {
    private final byte[] setProductOffers;
    private final byte[] setProductOffersSigned;
    private final PublicKey signaturePubKey;
    private final EncryptedOffersSet setOffers;

    public EncryptedOffersProductSigned(Signature signature, PublicKey signaturePubKey, byte[] setProductOffers, EncryptedOffersSet list) throws GeneralSecurityException {
        this.setOffers = list;
        this.setProductOffers = setProductOffers;
        this.setProductOffersSigned = SignatureUtil.signData(setProductOffers, signature);
        this.signaturePubKey = signaturePubKey;
    }

    public byte[] getSetProductOffers() {
        return setProductOffers;
    }

    public byte[] getSetProductOffersSigned() {
        return setProductOffersSigned;
    }

    public PublicKey getSignaturePubKey() {
        return signaturePubKey;
    }

    public EncryptedOffersSet getSetOffers() {
        return setOffers;
    }
}
