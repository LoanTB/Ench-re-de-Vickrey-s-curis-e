package com.projetenchere.common.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.security.*;

public class SignatureUtilTests {

    @Test
    public void testInitSignatureForSigning() throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();

        Signature signature = SignatureUtil.initSignatureForSigning(privateKey);

        Assertions.assertNotNull(signature);
        Assertions.assertEquals("SHA256withRSA", signature.getAlgorithm());
    }

    @Test
    public void testSignData() throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();

        Signature signature = SignatureUtil.initSignatureForSigning(privateKey);
        byte[] data = "Test Data".getBytes();
        byte[] signedData = SignatureUtil.signData(data, signature);

        Assertions.assertNotNull(signedData);
        Assertions.assertTrue(signedData.length > 0);
    }

    @Test
    public void testSignDataObject() throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();

        Signature signature = SignatureUtil.initSignatureForSigning(privateKey);
        String data = "Test Data";
        byte[] signedData = SignatureUtil.signData(data, signature);

        Assertions.assertNotNull(signedData);
        Assertions.assertTrue(signedData.length > 0);
    }

    @Test
    public void testObjectToArrayByte() {
        String data = "Test Data";
        byte[] byteArray = SignatureUtil.objectToArrayByte(data);

        Assertions.assertNotNull(byteArray);
        Assertions.assertTrue(byteArray.length > 0);
    }

    @Test
    public void testVerifyDataSignature() throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        Signature signature = SignatureUtil.initSignatureForSigning(privateKey);
        byte[] data = "Test Data".getBytes();
        byte[] signedData = SignatureUtil.signData(data, signature);

        boolean isVerified = SignatureUtil.verifyDataSignature(data, signedData, publicKey);

        Assertions.assertTrue(isVerified);
    }
}
