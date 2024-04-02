package com.projetenchere.common.Utils;

import com.projetenchere.common.Models.Encrypted.DJ.DJCipher;
import com.projetenchere.common.Models.Encrypted.DJ.DJKeyPairGenerator;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.*;

public class EncryptionUtil {

    public static KeyPair generateKeyPair() {
        DJKeyPairGenerator keyPairGenerator = new DJKeyPairGenerator(2048, new SecureRandom());
        return keyPairGenerator.generateKeyPair();
    }

    public static byte[] toByteArray(double value) {
        byte[] bytes = new byte[8];
        ByteBuffer.wrap(bytes).putDouble(value);
        return bytes;
    }
    public static double toDouble(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getDouble();
    }

    public static byte[] encryptPrice(double number, PublicKey publicKey) {
        DJCipher cp = new DJCipher(new SecureRandom());
        cp.init(publicKey);
        byte[] bytes = toByteArray(number);
        return cp.encrypt(new BigInteger(bytes)).toByteArray();
    }

    public static double decryptPrice(byte[] encryptedNumber, PrivateKey privateKey) {
        DJCipher cp = new DJCipher(new SecureRandom());
        cp.init(privateKey);
        return toDouble(cp.decrypt(new BigInteger(encryptedNumber)).toByteArray());
    }
}
