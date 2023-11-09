package com.projetenchere.common.Util;

import javax.crypto.Cipher;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.nio.ByteBuffer;

public class EncryptionUtils {
    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

    public static byte[] encrypt(double number, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] bytes = ByteBuffer.allocate(Double.BYTES).putDouble(number).array();
        return cipher.doFinal(bytes);
    }

    public static double decrypt(byte[] encryptedNumber, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedNumber);
        ByteBuffer wrapped = ByteBuffer.wrap(decryptedBytes);
        return wrapped.getDouble();
    }
}
