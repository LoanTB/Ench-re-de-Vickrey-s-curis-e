package com.projetenchere.common.Utils;

import com.projetenchere.common.Models.Encrypted.EncryptedOffersSet;
import com.projetenchere.common.Models.Encrypted.SignedEncryptedOfferSet;

import java.io.*;
import java.security.Signature;
import java.security.PublicKey;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

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

    public static byte[] signData(byte[] data, Signature signature) throws SignatureException {
        signature.update(data);
        return signature.sign();
    }

    public static byte[] signData(Object data, Signature signature) throws SignatureException {
        return signData(objectToArrayByte(data),signature);
    }

    public static byte[] objectToArrayByte(Object objetSerializable){
        byte[] tab = new byte[0];
        try{
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(objetSerializable);
            tab= baos.toByteArray();
            oos.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return tab;
    }

    public static EncryptedOffersSet toObject(byte[] tab){
        EncryptedOffersSet objet = null;
        try{
            ByteArrayInputStream bais = new ByteArrayInputStream(tab);
            ObjectInputStream ois = new ObjectInputStream(bais);
            objet = (EncryptedOffersSet) ois.readObject();
            ois.close();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return objet;
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

    public static boolean verifyDataSignature(EncryptedOffersSet data, byte[] signatureBytes, PublicKey publicKey) throws SignatureException {
        try {
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initVerify(publicKey);
            signature.update(objectToArrayByte(data));
            return signature.verify(signatureBytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new SignatureException(e);
        }
    }
}
