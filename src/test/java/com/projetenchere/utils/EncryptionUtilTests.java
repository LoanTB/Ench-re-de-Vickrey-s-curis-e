package com.projetenchere.utils;

import com.projetenchere.common.Utils.EncryptionUtil;
import org.junit.jupiter.api.*;

import java.nio.ByteBuffer;
import java.security.KeyPair;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

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
    void testEncryptDecryptPrice() throws Exception {
        double decryptedData = EncryptionUtil.decryptPrice(EncryptionUtil.encryptPrice(data, keys.getPublic()), keys.getPrivate());
        assertEquals(data, decryptedData);
    }

    @Test
    void testEncryptPriceAltersData() throws Exception {
        byte[] encryptedData = EncryptionUtil.encryptPrice(data, keys.getPublic());
        assertNotEquals(data, bytesToDouble(encryptedData));
    }

    @Test
    void testEncryptDecrypt() throws Exception {
        byte[] decryptedData = EncryptionUtil.decrypt(EncryptionUtil.encrypt(byteData, keys.getPublic()), keys.getPrivate());
        assertArrayEquals(byteData, decryptedData);
    }

    @Test
    void testEncryptAltersData() throws Exception {
        byte[] encryptedData = EncryptionUtil.encrypt(byteData, keys.getPublic());
        assertNotEquals(byteData, encryptedData);
    }

    private static double bytesToDouble(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        buffer.put(bytes);
        buffer.flip();
        return buffer.getDouble();
    }
}
