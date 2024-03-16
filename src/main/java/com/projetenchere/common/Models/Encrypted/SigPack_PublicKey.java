package com.projetenchere.common.Models.Encrypted;

import com.projetenchere.common.Utils.SignatureUtil;

import java.io.Serializable;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

public class SigPack_PublicKey extends AbstractSignedPack implements Serializable {

    //TODO : JavaDoc

    /**
     *
     * @param ok
     * @param okSigned
     * @param signaturePubKey
     */
    public SigPack_PublicKey(String ok, byte[] okSigned, PublicKey signaturePubKey){
        super(ok,okSigned,signaturePubKey);
    }
    //"ok", SignatureUtil.signData("ok".getBytes(), signature),
}
