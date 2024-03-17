package com.projetenchere.common.Models.Encrypted.DJ;

import com.sun.javafx.scene.paint.GradientUtils;

import java.math.BigInteger;
import java.security.*;

public class DJKeyPairGenerator{
    int size;
    SecureRandom random;
    BigInteger p, q, N;

    public DJKeyPairGenerator(int keysize, SecureRandom random) {
        this.size = keysize;
        while (this.size % 8 != 0) this.size++;
        this.random = random;
        p = BigInteger.probablePrime(size-1, random); // -1 is to get rid of the offset
        q = BigInteger.probablePrime(size-1, random);
    }

    public KeyPair generateKeyPair() {
        int s = 4;

        N = p.multiply(q);
        DJPublicKey pk = new DJPublicKey(N.toByteArray());
        byte[] pByteArray = this.p.toByteArray();
        byte[] qByteArray = q.toByteArray();
        byte[] skBytes = new byte[pByteArray.length + qByteArray.length];



        for (int i = 0; i < pByteArray.length; i++) {
            skBytes[i] = pByteArray[i];
            skBytes[i + pByteArray.length] = qByteArray[i];
        }

        DJPrivateKey sk = new DJPrivateKey(skBytes);
        return new KeyPair(pk, sk);
    }
}
