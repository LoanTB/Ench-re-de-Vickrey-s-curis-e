package com.projetenchere.common.Models.Encrypted;

import com.projetenchere.common.Utils.SignatureUtil;

import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.security.Signature;

public class SignedEncryptedOfferSet implements Serializable {

    private final byte[] setSigned;
    private final EncryptedOffersSet set;
    private final PublicKey signaturePubKey;

    public SignedEncryptedOfferSet(Signature signature, PublicKey signaturePubKey, EncryptedOffersSet list) throws GeneralSecurityException {
        this.set = list;
        this.setSigned = SignatureUtil.signData(list, signature);
        this.signaturePubKey = signaturePubKey;
    }

    public EncryptedOffersSet getSet() {
        return set;
    }

    public byte[] getSetSigned() {
        return setSigned;
    }

    public PublicKey getSignaturePubKey() {
        return signaturePubKey;
    }
}
