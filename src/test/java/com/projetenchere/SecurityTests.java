package com.projetenchere;

import com.projetenchere.common.Utils.EncryptionUtil;
import org.junit.jupiter.api.*;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.*;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class SecurityTests {
    @Nested
    @DisplayName("Encryption")
    public class TestEncryption {
        private static KeyPair keys;
        private static double data;

        @BeforeAll
        static void setupTest() {
            keys = EncryptionUtil.generateKeyPair();
            data = 123.456789;
        }

        @Test
        @Timeout(value = 50, unit = TimeUnit.MILLISECONDS)
        void encryptionWork() throws Exception {
            EncryptionUtil.decrypt(EncryptionUtil.encryptPrice(data, keys.getPublic()), keys.getPrivate());
        }

        @Test
        void encryptionAltersData() throws GeneralSecurityException {
            byte[] bytes = EncryptionUtil.encryptPrice(data, keys.getPublic());
            assertNotEquals(data,bytesToDouble(bytes));
        }

        @Test
        void decryptionRecoversData() throws Exception {
            assertEquals(data, EncryptionUtil.decryptPrice(EncryptionUtil.encryptPrice(data, keys.getPublic()),keys.getPrivate()));
        }

        private static double bytesToDouble(byte[] bytes) {
            ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
            buffer.put(bytes);
            buffer.flip();
            return buffer.getDouble();
        }

        public static BigInteger[] factorize(BigInteger n) {
            BigInteger x = BigInteger.valueOf(2);
            BigInteger y = x;
            BigInteger c = BigInteger.valueOf(1);
            BigInteger d = BigInteger.ZERO;
            while (true) {
                x = x.multiply(x).mod(n).add(c).mod(n);
                d = y.subtract(x).mod(n);
                BigInteger g = d.gcd(n);
                if (!g.equals(BigInteger.ONE)) {return new BigInteger[] {g, n.divide(g)};}
                if (y.equals(x)) {return new BigInteger[] {n, BigInteger.ONE};}
                y = x;
            }
        }

        public static BigInteger crack(BigInteger modulus, BigInteger publicExponent){
            BigInteger[] factors = factorize(modulus);
            if (factors[0].compareTo(BigInteger.ONE) > 0) {throw new RuntimeException("Failed to crack RSA key.");}
            BigInteger p = factors[0];BigInteger q = factors[1];
            return publicExponent.modInverse(p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)));
        }
    }

}
