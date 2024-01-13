package com.projetenchere.common.Models.Encrypted;

import com.projetenchere.common.Utils.SignatureUtil;

import java.io.Serializable;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

public class SignedPublicKey implements Serializable {
    private final byte[] okSigned;
    private final PublicKey publicKey;

    public SignedPublicKey(PublicKey publicKey, Signature signature) throws SignatureException {
        this.okSigned = SignatureUtil.signData("ok".getBytes(), signature);
        this.publicKey = publicKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public byte[] getOkSigned() {
        return okSigned;
    }
}
