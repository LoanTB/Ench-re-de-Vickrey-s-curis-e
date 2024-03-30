package com.projetenchere.common.models.SignedPack;

import com.projetenchere.common.Models.SignedPack.Set_SigPackEncOffer;
import com.projetenchere.common.Models.SignedPack.SigPack_EncOffer;
import com.projetenchere.common.Models.SignedPack.SigPack_EncOffersProduct;
import com.projetenchere.common.Utils.EncryptionUtil;
import com.projetenchere.common.Utils.KeyFileUtilWithJKS;
import com.projetenchere.common.Utils.SignatureUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SigPack_EncOffersProductTests {

    private SigPack_EncOffersProduct classUnderTest;

    private byte[] encPrice;
    private byte[] signedPrice;
    private PublicKey signaturePublicKeyFromKeyStore;
    private byte[] encPrice2;
    private byte[] signedPrice2;
    private PublicKey signaturePublicKeyFromKeyStore2;


    private Set_SigPackEncOffer setSigPackEncOffer;
    private BigInteger b;
    private byte[] bsigned;
    private PublicKey signaturePublicKeyFromKeyStore3;



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

        SigPack_EncOffer sigPackEncOffer = new SigPack_EncOffer(encPrice,signedPrice,signaturePublicKeyFromKeyStore,"0");
    //------------------------------------------------------------

        KeyFileUtilWithJKS keyFile2 = new KeyFileUtilWithJKS("_test2");
        if (!keyFile2.isKeyPairSaved()) {
            keyFile2.generateAndSaveKeyPair();
        }
        signaturePublicKeyFromKeyStore2 = keyFile2.getPublicKeyFromFile();
        PrivateKey signaturePrivateKeyFromKeyStore2 = keyFile2.getPrivateKeyFromFile();
        Signature signature2 = SignatureUtil.initSignatureForSigning(signaturePrivateKeyFromKeyStore2);

        KeyPair kp2 = EncryptionUtil.generateKeyPair();
        PublicKey puk2 = kp2.getPublic();

        encPrice2 = EncryptionUtil.encryptPrice(19.0,puk2);
        signedPrice2 = SignatureUtil.signData(encPrice2,signature2);

        SigPack_EncOffer sigPackEncOffer2 = new SigPack_EncOffer(encPrice2,signedPrice2,signaturePublicKeyFromKeyStore2,"0");


    //------------------------------------------------------------
        Set<SigPack_EncOffer> offers = new HashSet<>();
        offers.add(sigPackEncOffer2);
        offers.add(sigPackEncOffer);
        setSigPackEncOffer = new Set_SigPackEncOffer("0",offers);

    //------------------------------------

        b = new BigInteger(encPrice);
        b.multiply(new BigInteger(encPrice2));



        KeyFileUtilWithJKS keyFile3 = new KeyFileUtilWithJKS("_test3");
        if (!keyFile3.isKeyPairSaved()) {
            keyFile3.generateAndSaveKeyPair();
        }
        signaturePublicKeyFromKeyStore3 = keyFile3.getPublicKeyFromFile();
        PrivateKey signaturePrivateKeyFromKeyStore3 = keyFile3.getPrivateKeyFromFile();
        Signature signature3 = SignatureUtil.initSignatureForSigning(signaturePrivateKeyFromKeyStore3);

        bsigned = SignatureUtil.signData(b,signature3);



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
        KEYSTORE_FILEPATH = configPath + "/config_signature_keypair_test3.jks";
        CERT_FILEPATH = configPath + "/config_signature_certificat_test3.cer";
        KeyFileUtilWithJKS.executeCommand("rm "+ CERT_FILEPATH);
        KeyFileUtilWithJKS.executeCommand("rm "+ KEYSTORE_FILEPATH);
    }


    @Test
    void testsVerifySignatureWithOneOffer() throws Exception {

        classUnderTest = new SigPack_EncOffersProduct(b,bsigned,signaturePublicKeyFromKeyStore3,setSigPackEncOffer);

        assertAll(
                () -> assertTrue(SignatureUtil.verifyDataSignature(SignatureUtil.objectToArrayByte(b),bsigned,signaturePublicKeyFromKeyStore3)),
                () -> assertTrue(SignatureUtil.verifyDataSignature(SignatureUtil.objectToArrayByte(classUnderTest.getObject()),classUnderTest.getObjectSigned(), classUnderTest.getSignaturePubKey()))
        );

    }

    @Test
    void testArguments() throws GeneralSecurityException {
        classUnderTest = new SigPack_EncOffersProduct(b,bsigned,signaturePublicKeyFromKeyStore3,setSigPackEncOffer);
        assertEquals(setSigPackEncOffer,classUnderTest.getSetOffers());
        assertEquals(b,classUnderTest.getObject());
        assertEquals(bsigned,classUnderTest.getObjectSigned());
        assertEquals(signaturePublicKeyFromKeyStore3,classUnderTest.getSignaturePubKey());
    }


}
