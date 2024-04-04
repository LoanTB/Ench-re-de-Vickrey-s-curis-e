package com.projetenchere.common.model.signedPack;

import com.projetenchere.common.util.EncryptionUtil;
import com.projetenchere.common.util.SignatureUtil;
import com.projetenchere.common.util.keyFile.KeyFileUtilWithJKS;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

import static org.junit.jupiter.api.Assertions.*;

public class SigPack_EncOfferTests {

    private SigPack_EncOffer classUnderTest;

    private byte[] encPrice;

    private byte[] signedPrice;

    private PublicKey signaturePublicKeyFromKeyStore;


    @BeforeEach
    void initKeys() throws Exception {

        KeyFileUtilWithJKS keyFile = new KeyFileUtilWithJKS("_test");
        if (!keyFile.isKeyPairSaved()) {
            keyFile.generateAndSaveKeyPair();
        }
        signaturePublicKeyFromKeyStore = keyFile.getPublicKeyFromFile();
        PrivateKey signaturePrivateKeyFromKeyStore = keyFile.getPrivateKeyFromFile();
        Signature signature = SignatureUtil.initSignatureForSigning(signaturePrivateKeyFromKeyStore);

        KeyPair kp = EncryptionUtil.generateKeyPair();
        PublicKey puk = kp.getPublic();
        PrivateKey prk = kp.getPrivate();

        encPrice = EncryptionUtil.encryptPrice(18.0,puk);
        signedPrice = SignatureUtil.signData(encPrice,signature);

    }
    @AfterEach
    void closeKeys() throws IOException, InterruptedException {
        String configPath = "";
        String userHome = System.getProperty("user.home");
        configPath = userHome + "/.config/securewin";
        String KEYSTORE_FILEPATH = configPath + "/config_signature_keypair_test.jks";
        String CERT_FILEPATH = configPath + "/config_signature_certificat_test.cer";
        KeyFileUtilWithJKS.executeCommand("rm "+ CERT_FILEPATH);
        KeyFileUtilWithJKS.executeCommand("rm "+ KEYSTORE_FILEPATH);
    }


    @Test
    void testsVerifySignatureWithOneOffer() {

        classUnderTest = new SigPack_EncOffer(encPrice,signedPrice,signaturePublicKeyFromKeyStore,"0");
        assertAll(
            () -> assertTrue(SignatureUtil.verifyDataSignature(encPrice,signedPrice,signaturePublicKeyFromKeyStore)),
            () -> assertFalse(SignatureUtil.verifyDataSignature(SignatureUtil.objectToArrayByte(encPrice),signedPrice,signaturePublicKeyFromKeyStore)),
            () -> assertTrue(SignatureUtil.verifyDataSignature((byte[]) classUnderTest.getObject(),classUnderTest.getObjectSigned(), classUnderTest.getSignaturePubKey())),
            () -> assertFalse(SignatureUtil.verifyDataSignature(SignatureUtil.objectToArrayByte(classUnderTest.getObject()),classUnderTest.getObjectSigned(), classUnderTest.getSignaturePubKey()))
        );

    }

    @Test
    void testArguments(){
        classUnderTest = new SigPack_EncOffer(encPrice,signedPrice,signaturePublicKeyFromKeyStore,"0");

        assertEquals(signaturePublicKeyFromKeyStore,classUnderTest.getSignaturePubKey());
        assertEquals(encPrice,classUnderTest.getObject());
        assertEquals(signedPrice,classUnderTest.getObjectSigned());


    }



}
