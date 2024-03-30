package com.projetenchere.common.models.SignedPack;

import com.projetenchere.Seller.Model.Seller;
import com.projetenchere.common.Models.SignedPack.SigPack_EncOffer;
import com.projetenchere.common.Utils.EncryptionUtil;
import com.projetenchere.common.Utils.KeyFileUtilWithJKS;
import com.projetenchere.common.Utils.SignatureUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SigPack_EncOfferTests {

    private SigPack_EncOffer classUnderTest;

    private byte[] encPrice;

    private byte[] signedPrice;



    static Stream<Arguments> inputStreamEncOffer() throws Exception {
        return Stream.of(
                Arguments.of(15.0),
                Arguments.of(18.0)
        );
    }


    @ParameterizedTest
    @MethodSource("inputStreamEncOffer")
    void testsVerifySignatureWithOneOffer( double price) throws Exception {

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

        encPrice = EncryptionUtil.encryptPrice(price,puk);
        signedPrice = SignatureUtil.signData(encPrice,signature);

        classUnderTest = new SigPack_EncOffer(encPrice,signedPrice,signaturePublicKeyFromKeyStore,"0");


        assertTrue(SignatureUtil.verifyDataSignature(encPrice,signedPrice,signaturePublicKeyFromKeyStore));



        String configPath = "";
        String userHome = System.getProperty("user.home");
        configPath = userHome + "/.config/securewin";
        String KEYSTORE_FILEPATH = configPath + "/config_signature_keypair_test.jks";
        String CERT_FILEPATH = configPath + "/config_signature_certificat_test.cer";
        KeyFileUtilWithJKS.executeCommand("rm "+ CERT_FILEPATH);
        KeyFileUtilWithJKS.executeCommand("rm "+ KEYSTORE_FILEPATH);

    }

}
