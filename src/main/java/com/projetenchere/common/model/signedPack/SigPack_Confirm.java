package com.projetenchere.common.model.signedPack;

import java.io.Serializable;
import java.security.PublicKey;

public class SigPack_Confirm extends AbstractSignedPack implements Serializable {

    private final String bidId;
    /**
     * @param ok
     * @param okSigned
     * @param signaturePubKey
     */
    public SigPack_Confirm(int ok, byte[] okSigned, PublicKey signaturePubKey, String bidId) {
        super(ok, okSigned, signaturePubKey);
        this.bidId = bidId;
    }
    public synchronized String getBidId() {
        return bidId;
    }
}
