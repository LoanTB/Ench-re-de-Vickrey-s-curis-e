package com.projetenchere.common.model.encryption;

import com.projetenchere.common.model.encryption.dj.DJCipher;
import com.projetenchere.common.model.encryption.dj.DJKeyPairGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DJCipherTest {
    DJKeyPairGenerator keyPairGenerator;
    PrivateKey sk;
    PublicKey pk;
    DJCipher cp;
    @BeforeEach
    void setup() {
        keyPairGenerator = new DJKeyPairGenerator(2048, new SecureRandom());
        KeyPair kp = keyPairGenerator.generateKeyPair();
        sk = kp.getPrivate();
        pk = kp.getPublic();
        this.cp = new DJCipher(new SecureRandom());
    }

    @Test
    void encryptDecryptTest() {
        cp.init(pk);
        BigInteger plain = new BigInteger(2048, new SecureRandom());
        BigInteger encrypted = cp.encrypt(plain);
        cp.init(sk);
        assertEquals(plain, cp.decrypt(encrypted));
    }

    @Test
    void mustBeLinearlyHomomorphous() {
        cp.init(pk);
        BigInteger plain = new BigInteger(2048, new SecureRandom());
        BigInteger offset = new BigInteger(2048, new SecureRandom());
        BigInteger encrypted = cp.encrypt(plain).multiply(cp.encrypt(offset));
        cp.init(sk);
        assertEquals(plain.add(offset), cp.decrypt(encrypted));
    }
}