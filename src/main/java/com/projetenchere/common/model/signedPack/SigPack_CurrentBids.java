package com.projetenchere.common.model.signedPack;

import com.projetenchere.common.model.CurrentBids;

import java.io.Serializable;
import java.security.PublicKey;

public class SigPack_CurrentBids extends AbstractSignedPack implements Serializable {
    /**
     * @param object
     * @param objectSigned
     * @param signaturePubKey
     */
    public SigPack_CurrentBids(CurrentBids object, byte[] objectSigned, PublicKey signaturePubKey) {
        super(object, objectSigned, signaturePubKey);
    }
}
