package com.projetenchere.common.Models.Encrypted;

import java.io.Serializable;
import java.security.PublicKey;

//TODO : JavaDoc
public abstract class AbstractSignedPack implements Serializable {
    protected final Object object;
    protected final byte[] objectSigned;
    protected final PublicKey signaturePubKey;

    /**
     * @param object
     * @param objectSigned
     * @param signaturePubKey
     */
    protected AbstractSignedPack(Object object, byte[] objectSigned, PublicKey signaturePubKey) {
        this.object = object;
        this.objectSigned = objectSigned;
        this.signaturePubKey = signaturePubKey;
    }

    /**
     * @return
     */
    public Object getObject() {
        return object;
    }

    /**
     * @return
     */
    public byte[] getObjectSigned() {
        return objectSigned;
    }

    /**
     * @return
     */
    public PublicKey getSignaturePubKey() {
        return signaturePubKey;
    }
}