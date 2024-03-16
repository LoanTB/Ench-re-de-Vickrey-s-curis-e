package com.projetenchere.common.Models.Encrypted;

import com.projetenchere.common.Models.Encrypted.DJ.DJCipher;
import com.projetenchere.common.Models.Encrypted.DJ.DJKeyPairGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.sql.Array;
import java.util.ArrayList;

public class DJCipherTest {
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
    void decryptTest() {
        BigInteger data = BigInteger.valueOf(5555);
        DJCipher cp = new DJCipher();
        cp.init(DJCipher.Modes.DECRYPT_MODE, sk);
        cp.decrypt(data);
    }
}