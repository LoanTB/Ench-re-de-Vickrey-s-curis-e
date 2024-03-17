package com.projetenchere.common.Models.SignedPack;

import java.io.Serializable;
import java.security.PublicKey;

public class SigPack_Confirm extends AbstractSignedPack implements Serializable {

    //TODO S2 : JavaDoc
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
    public String getBidId() {
        return bidId;
    }
}
