package com.projetenchere.common.Models.Encrypted;

import com.projetenchere.common.Models.Encrypted.DJ.DJKeyPairGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DJKeyPairGeneratorTest {

    DJKeyPairGenerator keyPairGenerator;
    PrivateKey sk;
    PublicKey pk;

    @BeforeEach
    void setup() {
        keyPairGenerator = new DJKeyPairGenerator(2048, new SecureRandom());
        KeyPair kp = keyPairGenerator.generateKeyPair();
        sk = kp.getPrivate();
        pk = kp.getPublic();
    }

    @Test
    void pkLenqthMustBe512() {
        assertEquals(512, sk.getEncoded().length);
    }

    @Test
    void keysMustMatch() {
        byte[] pBytes = new byte[256];
        byte[] qBytes = new byte[256];
        for (int i=0; i < pBytes.length; i++) {
            pBytes[i] = sk.getEncoded()[i];
            qBytes[i] = sk.getEncoded()[i+pBytes.length];
        }
        BigInteger p = new BigInteger(pBytes);
        BigInteger q = new BigInteger(qBytes);
        BigInteger N = p.multiply(q);
        assertEquals(new BigInteger(pk.getEncoded()).pow(4), N.pow(4));
    }
}
