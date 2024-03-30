package com.projetenchere.common.models.SignedPack;

import com.projetenchere.common.Models.SignedPack.SigPack_PriceWin;
import com.projetenchere.common.Models.SignedPack.SigPack_Results;
import com.projetenchere.common.Utils.EncryptionUtil;
import com.projetenchere.common.Utils.KeyFile.KeyFileUtilWithJKS;
import com.projetenchere.common.Utils.SignatureUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SigPack_PriceResultsTests {

    private SigPack_Results classUnderTest;


    private byte[] encPrice;
    private byte[] signedPrice;
    private PublicKey signaturePublicKeyFromKeyStore;

    private SigPack_PriceWin sigPackPriceWin;

    private PublicKey signaturePublicKeyFromKeyStore2;
    private Signature signature2;

    private byte[] resultsSigned;
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

        encPrice = EncryptionUtil.encryptPrice(18.0,puk);
        signedPrice = SignatureUtil.signData(encPrice,signature);

        sigPackPriceWin = new SigPack_PriceWin(18.0,signedPrice,signaturePublicKeyFromKeyStore,encPrice,"0");




        KeyFileUtilWithJKS keyFile2 = new KeyFileUtilWithJKS("_test2");
        if (!keyFile2.isKeyPairSaved()) {
            keyFile2.generateAndSaveKeyPair();
        }
        signaturePublicKeyFromKeyStore2 = keyFile2.getPublicKeyFromFile();
        PrivateKey signaturePrivateKeyFromKeyStore2 = keyFile2.getPrivateKeyFromFile();
        signature2 = SignatureUtil.initSignatureForSigning(signaturePrivateKeyFromKeyStore2);

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
         KEYSTORE_FILEPATH = configPath + "/config_signature_keypair_test2.jks";
         CERT_FILEPATH = configPath + "/config_signature_certificat_test2.cer";
        KeyFileUtilWithJKS.executeCommand("rm "+ CERT_FILEPATH);
        KeyFileUtilWithJKS.executeCommand("rm "+ KEYSTORE_FILEPATH);
    }

    @Test
    void testsVerifySignatureWithOneOffer() throws Exception {
        resultsSigned = SignatureUtil.signData(sigPackPriceWin.getObject(),signature2);

        classUnderTest = new SigPack_Results(sigPackPriceWin,resultsSigned,signaturePublicKeyFromKeyStore2,"0");

        assertAll(
                () -> assertTrue(SignatureUtil.verifyDataSignature(SignatureUtil.objectToArrayByte(sigPackPriceWin.getObject()),resultsSigned,signaturePublicKeyFromKeyStore2)),
                () -> assertTrue(SignatureUtil.verifyDataSignature(SignatureUtil.objectToArrayByte(((SigPack_PriceWin)classUnderTest.getObject()).getObject()),
                        classUnderTest.getObjectSigned(),classUnderTest.getSignaturePubKey())),
                () -> assertFalse(SignatureUtil.verifyDataSignature(SignatureUtil.objectToArrayByte(classUnderTest.getObject()),classUnderTest.getObjectSigned(),classUnderTest.getSignaturePubKey()))
        );
    }

    @Test
    void testArguments() throws GeneralSecurityException {

        //assertEquals();

    }


}
