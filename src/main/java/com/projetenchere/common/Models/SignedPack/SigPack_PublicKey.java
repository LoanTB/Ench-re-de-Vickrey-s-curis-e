package com.projetenchere.common.Models.SignedPack;

import java.io.Serializable;
import java.security.PublicKey;

public class SigPack_PublicKey extends AbstractSignedPack implements Serializable {

    //TODO : JavaDoc

    /**
     * @param ok
     * @param okSigned
     * @param signaturePubKey
     */
    public SigPack_PublicKey(String ok, byte[] okSigned, PublicKey signaturePubKey) {
        super(ok, okSigned, signaturePubKey);
    }
}
