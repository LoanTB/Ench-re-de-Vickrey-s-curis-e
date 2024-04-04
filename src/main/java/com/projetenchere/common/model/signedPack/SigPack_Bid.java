package com.projetenchere.common.model.signedPack;

import com.projetenchere.common.model.Bid;

import java.io.Serializable;
import java.security.PublicKey;

public class SigPack_Bid extends AbstractSignedPack implements Serializable {
    /**
     * @param object
     * @param objectSigned
     * @param signaturePubKey
     */
    public SigPack_Bid(Bid object, byte[] objectSigned, PublicKey signaturePubKey) {
        super(object, objectSigned, signaturePubKey);
    }
}
