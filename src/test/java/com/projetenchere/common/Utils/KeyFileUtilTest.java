package com.projetenchere.common.Utils;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class KeyFileUtilTest {

    //JKS
    @Test
    public void testSaveAndGetKeyPairWithJKS() {
        try {
            KeyFileUtilWithJKS keyFile = new KeyFileUtilWithJKS();

            if (!keyFile.isKeyPairSaved()) {
                assertFalse(keyFile.isKeyPairSaved());
                keyFile.generateAndSaveKeyPair();
            }

            assertTrue(keyFile.isKeyPairSaved());

            PublicKey publicKeyFromKeyStore = keyFile.getPublicKeyFromFile();
            assertNotNull(publicKeyFromKeyStore);

            PrivateKey privateKeyFromKeyStore = keyFile.getPrivateKeyFromFile();
            assertNotNull(privateKeyFromKeyStore);

        } catch (Exception e) {
            fail("Exception: " + e.getMessage());
        }
    }

    @Test
    public void testGetPrivateKeyWithJKSIsAlwaysTheSame() throws Exception {
        KeyFileUtilWithJKS keyFile = new KeyFileUtilWithJKS();

        if (!keyFile.isKeyPairSaved()) {
            assertFalse(keyFile.isKeyPairSaved());
            keyFile.generateAndSaveKeyPair();
        }

        PrivateKey privateKeyFromKeyStore = keyFile.getPrivateKeyFromFile();
        assertNotNull(privateKeyFromKeyStore);

        PrivateKey privateKeyFromKeyStore2 = keyFile.getPrivateKeyFromFile();
        assertNotNull(privateKeyFromKeyStore2);

        assertEquals(privateKeyFromKeyStore2, privateKeyFromKeyStore);

        PublicKey publicKeyFromStore = keyFile.getPublicKeyFromFile();
        PublicKey publicKeyFromStore2 = keyFile.getPublicKeyFromFile();

        assertNotNull(publicKeyFromStore);
        assertNotNull(publicKeyFromStore2);

        assertEquals(publicKeyFromStore2, publicKeyFromStore);

        String genKeyCommand = "keytool -genkeypair -alias SecureWin2 -keyalg RSA -keysize 2048 -keystore src/test/config/config_signature_keypair2.jks -validity 365 -dname \"CN=Secure, OU=Win, O=SecureWin, L=Montpellier, ST=Occitanie, C=FR\" -storepass SecureWinPaulLoanYukiRemiKatia -keypass SecureWinPaulLoanYukiRemiKatia";
        String exportCertCommand = "keytool -export -alias SecureWin2 -file src/test/config/config_signature_certificat2.cer -keystore src/test/config/config_signature_keypair2.jks -storepass SecureWinPaulLoanYukiRemiKatia -keypass SecureWinPaulLoanYukiRemiKatia";

        KeyFileUtilWithJKS.executeCommand(genKeyCommand);
        KeyFileUtilWithJKS.executeCommand(exportCertCommand);

        KeyStore keyStore = KeyStore.getInstance("JKS");
        FileInputStream fis = new FileInputStream("src/test/config/config_signature_keypair2.jks");
        keyStore.load(fis, "SecureWinPaulLoanYukiRemiKatia".toCharArray());
        PrivateKey privateKey2 = (PrivateKey) keyStore.getKey("SecureWin2", "SecureWinPaulLoanYukiRemiKatia".toCharArray());

        FileInputStream fis2 = new FileInputStream("src/test/config/config_signature_certificat2.cer");
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate certificate = (X509Certificate) cf.generateCertificate(fis);
        PublicKey pubKey2 = certificate.getPublicKey();

        assertNotEquals(privateKey2, privateKeyFromKeyStore);
        assertNotEquals(pubKey2, publicKeyFromStore);
        fis2.close();
        fis.close();

    }

    @Test
    public void testSignWithKeyPairGetWithJKS() throws Exception {
        KeyFileUtilWithJKS keyFile = new KeyFileUtilWithJKS();
        if (!keyFile.isKeyPairSaved()) {
            keyFile.generateAndSaveKeyPair();
        }
        PrivateKey privKey = keyFile.getPrivateKeyFromFile();
        PublicKey pubKey = keyFile.getPublicKeyFromFile();


        KeyPair keyPairFraud = EncryptionUtil.generateKeyPair();

        byte[] object = new byte[10];
        Random random = new Random();
        random.nextBytes(object);

        Signature signature = SignatureUtil.initSignatureForSigning(privKey);
        // Création de l'outil de signature frauduleux
        Signature signatureFraud = SignatureUtil.initSignatureForSigning(keyPairFraud.getPrivate());
        // Récupération de la signature de l'objet à envoyer
        byte[] sign = SignatureUtil.signData(object, signature);
        // Récupération de la signature de l'objet frauduleux à envoyer
        byte[] signFraud = SignatureUtil.signData(object, signatureFraud);
        // Verification du signé
        // La clé est mauvaise.
        assertFalse(SignatureUtil.verifyDataSignature(object, sign, keyPairFraud.getPublic()));
        // La signature est mauvaise.
        assertFalse(SignatureUtil.verifyDataSignature(object, signFraud, pubKey));
        // La signature et la clé sont bonnes.
        assertTrue(SignatureUtil.verifyDataSignature(object, sign, pubKey));
    }


    //TXT STUB
    /*
    @Test
    public  void testGenerateAndSaveKeyWithTXT() throws Exception {
        KeyFileUtilWithTXT keyFile = new KeyFileUtilWithTXT();
    }

    @Test
    public  void testGetPrivateKeyWithTXT() throws Exception {
    }
    @Test
    public  void testGetPublicKeyWithTXT() throws Exception {
    }
    */
}
