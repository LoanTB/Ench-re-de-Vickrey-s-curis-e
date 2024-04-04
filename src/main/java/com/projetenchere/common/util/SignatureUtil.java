package com.projetenchere.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.*;

public class SignatureUtil {
    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";

    public static Signature initSignatureForSigning(PrivateKey privateKey) throws SignatureException {
        try {
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initSign(privateKey);
            return signature;
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new SignatureException(e);
        }
    }

    public synchronized static byte[] signData(byte[] data, Signature signature) throws SignatureException {
        signature.update(data);
        return signature.sign();
    }

    public synchronized static byte[] signData(Object data, Signature signature) throws SignatureException {
        return signData(objectToArrayByte(data), signature);
    }

    public synchronized static byte[] objectToArrayByte(Object objetSerializable) {
        byte[] tab = new byte[0];
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(objetSerializable);
            tab = baos.toByteArray();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tab;
    }

    public synchronized static boolean verifyDataSignature(byte[] data, byte[] signatureBytes, PublicKey publicKey) throws SignatureException {
        try {
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initVerify(publicKey);
            signature.update(data);
            return signature.verify(signatureBytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new SignatureException(e);
        }
    }

}
