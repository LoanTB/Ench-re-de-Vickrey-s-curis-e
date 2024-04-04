package com.projetenchere.common.model.signedPack;

import java.io.Serializable;
import java.security.PublicKey;

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
    public synchronized Object getObject() {
        return object;
    }

    /**
     * @return
     */
    public synchronized byte[] getObjectSigned() {
        return objectSigned;
    }

    /**
     * @return
     */
    public synchronized PublicKey getSignaturePubKey() {
        return signaturePubKey;
    }
}