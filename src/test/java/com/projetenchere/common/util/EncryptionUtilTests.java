package com.projetenchere.common.util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.nio.ByteBuffer;
import java.security.KeyPair;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class EncryptionUtilTests {

    private static KeyPair keys;
    private static double data;
    private static byte[] byteData;

    @BeforeAll
    static void setupTest() {
        keys = EncryptionUtil.generateKeyPair();
        data = 123.456789;
        byteData = "Test Data".getBytes();
    }

    @Test
    @Timeout(value = 50, unit = TimeUnit.MILLISECONDS)
    void testEncryptDecryptPrice() {
        double decryptedData = EncryptionUtil.decryptPrice(EncryptionUtil.encryptPrice(data, keys.getPublic()), keys.getPrivate());
        assertEquals(data, decryptedData);
    }

    @Test
    void testEncryptPriceAltersData() {
        byte[] encryptedData = EncryptionUtil.encryptPrice(data, keys.getPublic());
        assertNotEquals(data, bytesToDouble(encryptedData));
    }


    private static double bytesToDouble(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        buffer.put(bytes);
        buffer.flip();
        return buffer.getDouble();
    }
}
