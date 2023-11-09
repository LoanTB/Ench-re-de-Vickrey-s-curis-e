package com.projetenchere.common.Utils;

import java.security.Signature;
import java.security.PublicKey;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class SignatureUtils {
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

    public static byte[] signData(byte[] data, Signature signature) throws SignatureException {
        signature.update(data);
        return signature.sign();
    }

    public static boolean verifyDataSignature(byte[] data, byte[] signatureBytes, PublicKey publicKey) throws SignatureException {
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
