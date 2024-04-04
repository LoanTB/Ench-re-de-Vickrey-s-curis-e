package com.projetenchere.seller.model;

import com.projetenchere.common.model.signedPack.Set_SigPackEncOffer;
import com.projetenchere.common.model.signedPack.SigPack_EncOffer;
import com.projetenchere.common.util.EncryptionUtil;
import com.projetenchere.common.util.keyFile.KeyFileUtilWithJKS;
import com.projetenchere.common.util.SignatureUtil;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SellerTest {



    private Seller classUnderTest;





    @Test
    void testsVerifyAndAddOfferWithOneOffer() throws Exception {
    classUnderTest = Seller.getInstance();


        KeyFileUtilWithJKS keyFile = new KeyFileUtilWithJKS("_test");
        if (!keyFile.isKeyPairSaved()) {
            keyFile.generateAndSaveKeyPair();
        }
        PublicKey signaturePublicKeyFromKeyStore = keyFile.getPublicKeyFromFile();
        PrivateKey signaturePrivateKeyFromKeyStore = keyFile.getPrivateKeyFromFile();
        Signature signature = SignatureUtil.initSignatureForSigning(signaturePrivateKeyFromKeyStore);

        KeyPair kp = EncryptionUtil.generateKeyPair();
        PublicKey puk = kp.getPublic();
        PrivateKey prk = kp.getPrivate();

        byte[] encPrice = EncryptionUtil.encryptPrice(18.0,puk);
        byte[] signedPrice = SignatureUtil.signData(encPrice,signature);

        SigPack_EncOffer offer = new SigPack_EncOffer(encPrice,signedPrice,signaturePublicKeyFromKeyStore,"0");

        classUnderTest.setEncryptedOffers(new Set_SigPackEncOffer("0", new HashSet<>(),1));

        assertTrue(SignatureUtil.verifyDataSignature(SignatureUtil.objectToArrayByte(offer.getObject()),
                offer.getObjectSigned(), offer.getSignaturePubKey()));


        classUnderTest.verifyAndAddOffer(offer);

        assertFalse(classUnderTest.getEncryptedOffersSet().getOffers().isEmpty());


        String configPath = "";
        String userHome = System.getProperty("user.home");
        configPath = userHome + "/.config/securewin";
        String KEYSTORE_FILEPATH = configPath + "/config_signature_keypair_test.jks";
        String CERT_FILEPATH = configPath + "/config_signature_certificat_test.cer";
        KeyFileUtilWithJKS.executeCommand("rm "+ CERT_FILEPATH);
        KeyFileUtilWithJKS.executeCommand("rm "+ KEYSTORE_FILEPATH);

    }





}
