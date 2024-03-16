package com.projetenchere.common.Utils;

import com.projetenchere.common.Models.Encrypted.DJ.DJKeyPairGenerator;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.security.*;

public class EncryptionUtil {

    public static KeyPair generateKeyPair() {
        DJKeyPairGenerator keyPairGenerator = new DJKeyPairGenerator(2048, new SecureRandom());
        return keyPairGenerator.generateKeyPair();
    }

    public static byte[] encryptPrice(double number, PublicKey publicKey) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] bytes = ByteBuffer.allocate(Double.BYTES).putDouble(number).array();
        return cipher.doFinal(bytes);
    }

    public static double decryptPrice(byte[] encryptedNumber, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedNumber);
        ByteBuffer wrapped = ByteBuffer.wrap(decryptedBytes);
        return wrapped.getDouble();
    }

    public static byte[] encrypt(byte[] data, PublicKey publicKey) throws Exception {
        // Générer une clé AES aléatoire
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256); // AES-256
        SecretKey aesKey = keyGen.generateKey();

        // Chiffrer les données avec la clé AES
        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.ENCRYPT_MODE, aesKey);
        byte[] encryptedData = aesCipher.doFinal(data);

        // Chiffrer la clé AES avec RSA
        Cipher rsaCipher = Cipher.getInstance("RSA");
        rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedKey = rsaCipher.doFinal(aesKey.getEncoded());

        // Combiner la clé chiffrée et les données chiffrées
        ByteBuffer byteBuffer = ByteBuffer.allocate(4 + encryptedKey.length + encryptedData.length);
        byteBuffer.putInt(encryptedKey.length);
        byteBuffer.put(encryptedKey);
        byteBuffer.put(encryptedData);

        return byteBuffer.array();
    }

    public static byte[] decrypt(byte[] encryptedData, PrivateKey privateKey) throws Exception {
        ByteBuffer byteBuffer = ByteBuffer.wrap(encryptedData);

        int keyLength = byteBuffer.getInt();
        byte[] encryptedKey = new byte[keyLength];
        byteBuffer.get(encryptedKey);

        // Extraire les données chiffrées
        byte[] cipherText = new byte[byteBuffer.remaining()];
        byteBuffer.get(cipherText);

        // Déchiffrer la clé AES avec RSA
        Cipher rsaCipher = Cipher.getInstance("RSA");
        rsaCipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] aesKeyBytes = rsaCipher.doFinal(encryptedKey);
        SecretKey aesKey = new SecretKeySpec(aesKeyBytes, "AES");

        // Déchiffrer les données avec la clé AES
        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.DECRYPT_MODE, aesKey);
        return aesCipher.doFinal(cipherText);
    }
}
