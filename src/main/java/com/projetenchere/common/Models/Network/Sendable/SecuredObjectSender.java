package com.projetenchere.common.Models.Network.Sendable;

import com.projetenchere.common.Utils.EncryptionUtil;
import com.projetenchere.common.Utils.SerializationUtil;
import com.projetenchere.common.Utils.SignatureUtil;

import java.io.IOException;
import java.io.Serializable;
import java.security.PrivateKey;
import java.security.PublicKey;

public class SecuredObjectSender implements Serializable {
    private final byte[] encryptedObjectSender;
    private final byte[] signatureBytes;

    public SecuredObjectSender(ObjectSender objectSender, PrivateKey signaturePrivateKey, PublicKey encryptionPublicKey) throws Exception {
        encryptedObjectSender = EncryptionUtil.encrypt(SerializationUtil.serialize(objectSender),encryptionPublicKey);
        signatureBytes = SignatureUtil.signData(encryptedObjectSender,SignatureUtil.initSignatureForSigning(signaturePrivateKey));
    }

    public byte[] getEncryptedObjectSender() {
        return encryptedObjectSender;
    }

    public byte[] getSignatureBytes() {
        return signatureBytes;
    }
}

