package com.projetenchere.common.Models.Encrypted.DJ;

import jdk.jshell.spi.ExecutionControl;

import javax.crypto.*;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.AlgorithmParameterSpec;

public class DJCipher {

    public enum Modes {
        ENCRYPT_MODE,
        DECRYPT_MODE,
    };

    private Modes opMode;
    private AlgorithmParameters params;
    private SecureRandom random;
    private BigInteger phiN, N, p, q;
    public static final int s = 4;
    private BigInteger d;

    private BigInteger L(BigInteger b) {
        return b.subtract(BigInteger.ONE).divide(N);
    }


    public void init(Modes opMode, PublicKey pk) {
        this.opMode = opMode;
        //TODO
    }

    public void init(Modes opMode, PrivateKey sk) {
        this.opMode = opMode;
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
    public BigInteger decrypt(BigInteger ciphertext) {
        BigInteger M = ciphertext.modPow(phiN, N.pow(s + 1));
        BigInteger i = BigInteger.ZERO;
        for (int j = 1; j <= s; j++) {
            BigInteger t1 = L(M.mod(N.pow(j + 1)));
            BigInteger t2 = i;
            for (int k = 2; k <= j; k++) {
                i = i.subtract(BigInteger.ONE);
                t2 = t2.multiply(i).mod(N.pow(j));
                BigInteger t3 = t2.multiply(N.pow(k - 1));
                t1 = t1.subtract(t3.divide(factorial(BigInteger.valueOf(k)))).mod(N.pow(j));
            }
            i = t1;
        }
        return i.multiply(d).mod(N.pow(s));
    }

    private BigInteger factorial(BigInteger n) {
        if (n.equals(BigInteger.ONE) || n.equals(BigInteger.ZERO)) return BigInteger.ONE;
        return n.multiply(factorial(n.subtract(BigInteger.ONE)));
    }
}
