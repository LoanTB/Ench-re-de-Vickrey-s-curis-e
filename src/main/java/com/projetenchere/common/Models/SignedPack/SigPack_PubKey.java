package com.projetenchere.common.Models.SignedPack;

import java.io.Serializable;
import java.security.PublicKey;

public class SigPack_PubKey extends AbstractSignedPack implements Serializable {
    /**
     * @param object
     * @param objectSigned
     * @param signaturePubKey
     */
    public SigPack_PubKey(PublicKey object, byte[] objectSigned, PublicKey signaturePubKey) {
        super(object, objectSigned, signaturePubKey);
    }
}
