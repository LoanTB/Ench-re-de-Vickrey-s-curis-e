package com.projetenchere.common.Models.Encrypted.DJ;

import jdk.jshell.spi.ExecutionControl;

import javax.crypto.*;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Random;

public class DJCipher {

    public enum Modes {
        ENCRYPT_MODE,
        DECRYPT_MODE,
    };

    private Modes opMode;
    private AlgorithmParameters params;
    private final Random random;
    private BigInteger phiN, N, p, q;
    public static final int s = 2;
    private BigInteger d;

    private BigInteger L(BigInteger b) {
        return b.subtract(BigInteger.ONE).divide(N);
    }

    public DJCipher(Random random) {
        this.random = random;
    }

    public void init(PrivateKey sk) {

        byte[] pBytes = new byte[256];
        byte[] qBytes = new byte[256];
        for (int i=0; i < pBytes.length; i++) {
            pBytes[i] = sk.getEncoded()[i];
            qBytes[i] = sk.getEncoded()[i+pBytes.length];
        }
        this.p = new BigInteger(pBytes);
        this.q = new BigInteger(qBytes);
        this.N = p.multiply(q);
        this.phiN = p
                .subtract(BigInteger.ONE)
                .multiply(
                        q.subtract(BigInteger.ONE)
                );
        this.d = phiN.modInverse(N.pow(s));
    }

    public void init(PublicKey pk) {
        this.N = new BigInteger(pk.getEncoded());
    }

    public BigInteger encrypt(BigInteger plaintext) {
        BigInteger r = new BigInteger(N.bitLength(), random);
        return (BigInteger.ONE.add(N))
                .modPow(plaintext, N.pow(s+1))
                .multiply(
                        r.modPow(N.pow(s), N.pow(s+1))
                );
    }
    public BigInteger decrypt(BigInteger ciphertext) {
        BigInteger M = ciphertext.modPow(this.phiN, N.pow(s + 1));
        BigInteger i = BigInteger.ZERO;
        for (int j = 1; j <= s; j++) {
            BigInteger t1 = L(M.mod(N.pow(j + 1)));
            BigInteger t2 = i;
            for (int k = 2; k <= j; k++) {
                i = i.subtract(BigInteger.ONE);
                t2 = t2.multiply(i).mod(N.pow(j));
                BigInteger t3 = t2.multiply(N.pow(k - 1));
                t1 = t1.subtract(t3.divide(factorial(k))).mod(N.pow(j));
            }
            i = t1;
        }
        return i.multiply(d).mod(N.pow(s));
    }

    private BigInteger factorial(int n) {
        BigInteger result = BigInteger.ONE;
        for (int i = 2; i <= n; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }
}
