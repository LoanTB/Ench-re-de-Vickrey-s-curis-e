package com.projetenchere.Seller.Model;

import com.projetenchere.common.Models.SignedPack.Set_SigPackEncOffer;
import com.projetenchere.common.Models.SignedPack.SigPack_EncOffer;
import com.projetenchere.common.Utils.EncryptionUtil;
import com.projetenchere.common.Utils.KeyFileUtilWithJKS;
import com.projetenchere.common.Utils.SignatureUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;

import java.io.IOException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.HashSet;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class SellerTest {


    @InjectMocks
    private Seller classUnderTest;



    static Stream<Arguments> inputStreamEncOffer() throws Exception {
        return Stream.of(
                Arguments.of("test",15.0),
                Arguments.of("test",18.0)
                );
    }

    @ParameterizedTest
    @MethodSource("inputStreamEncOffer")
    void testsVerifyAndAddOfferWithOneOffer(String user, double price) throws Exception {
    classUnderTest = Seller.getInstance();


        KeyFileUtilWithJKS keyFile = new KeyFileUtilWithJKS("_"+user);
        if (!keyFile.isKeyPairSaved()) {
            keyFile.generateAndSaveKeyPair();
        }
        PublicKey signaturePublicKeyFromKeyStore = keyFile.getPublicKeyFromFile();
        PrivateKey signaturePrivateKeyFromKeyStore = keyFile.getPrivateKeyFromFile();
        Signature signature = SignatureUtil.initSignatureForSigning(signaturePrivateKeyFromKeyStore);

        KeyPair kp = EncryptionUtil.generateKeyPair();
        PublicKey puk = kp.getPublic();
        PrivateKey prk = kp.getPrivate();

        byte[] encPrice = EncryptionUtil.encryptPrice(price,puk);
        byte[] signedPrice = SignatureUtil.signData(encPrice,signature);

        SigPack_EncOffer offer = new SigPack_EncOffer(encPrice,signedPrice,signaturePublicKeyFromKeyStore,"0");


        classUnderTest.setEncryptedOffers(new Set_SigPackEncOffer("0", new HashSet<>()));


        classUnderTest.verifyAndAddOffer(offer);

        assertFalse(classUnderTest.getEncryptedOffersSet().getOffers().isEmpty());


        String configPath = "";
        String userHome = System.getProperty("user.home");
        configPath = userHome + "/.config/securewin";
        String KEYSTORE_FILEPATH = configPath + "/config_signature_keypair_"+user+".jks";
        String CERT_FILEPATH = configPath + "/config_signature_certificat_"+user+".cer";
        KeyFileUtilWithJKS.executeCommand("rm "+ CERT_FILEPATH);
        KeyFileUtilWithJKS.executeCommand("rm "+ KEYSTORE_FILEPATH);

    }





}
