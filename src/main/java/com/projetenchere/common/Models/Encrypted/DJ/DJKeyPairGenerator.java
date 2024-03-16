package com.projetenchere.common.Models.Encrypted.DJ;

import com.sun.javafx.scene.paint.GradientUtils;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGeneratorSpi;
import java.security.PrivateKey;
import java.security.SecureRandom;

public class DJKeyPairGenerator extends KeyPairGeneratorSpi {
    int size;
    SecureRandom random;
    @Override
    public void initialize(int keysize, SecureRandom random) {
        this.size = keysize;
        while (this.size % 8 != 0) this.size++;
        this.random = random;
    }

    @Override
    public KeyPair generateKeyPair() {
        BigInteger p, q, N;
        int s = 4;
        SecureRandom random = new SecureRandom();
        p = BigInteger.probablePrime(size, random);
        q = BigInteger.probablePrime(size, random);
        N = p.multiply(q);
        DJPublicKey pk = new DJPublicKey(N.pow(s).toByteArray());
        byte[] skBytes = new byte[p.toByteArray().length + q.toByteArray().length];

        for (int i = 0; i < skBytes.length; i++) {
            skBytes[i] = p.toByteArray()[i];
            skBytes[i + p.toByteArray().length] = q.toByteArray()[i];
        }

        DJPrivateKey sk = new DJPrivateKey(skBytes);
        return new KeyPair(pk, sk);
    }
}
