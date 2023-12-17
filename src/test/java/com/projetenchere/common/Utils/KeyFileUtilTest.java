package com.projetenchere.common.Utils;

import com.projetenchere.common.Utils.stub.KeyFileUtilWithTXT;
import org.junit.jupiter.api.Test;

import java.security.*;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class KeyFileUtilTest {


    //JKS
    @Test
    public void testSaveAndGetKeyPairWithJKS() {
        try {
            // Générer une nouvelle paire de clés
            KeyFileUtilWithJKS keyFile = new KeyFileUtilWithJKS();

            // Enregistrer la paire de clés dans un fichier .jks
            keyFile.generateAndSaveKeyPair();

            // Vérifier si la paire de clés a été enregistrée dans le fichier .jks
            assertTrue(keyFile.isKeyPairSaved());

            // Récupérer la clé privée du fichier .jks et vérifier si elle correspond à celle générée
            PrivateKey privateKeyFromKeyStore = keyFile.getPrivateKeyFromFile();
            //assertEquals(, privateKeyFromKeyStore);

            // Récupérer la clé publique du fichier .jks et vérifier si elle correspond à celle générée
            PublicKey publicKeyFromKeyStore = keyFile.getPublicKeyFromFile();
            //assertEquals(, publicKeyFromKeyStore);

        } catch (Exception e) {
            fail("Exception: " + e.getMessage());
        }
    }

    @Test
    public void testSignWithKeyPairGetWithJKS() throws Exception {
        KeyFileUtilWithJKS keyFile = new KeyFileUtilWithJKS();
        keyFile.generateAndSaveKeyPair();
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
        byte[] sign = SignatureUtil.signData(object,signature);
        // Récupération de la signature de l'objet frauduleux à envoyer
        byte[] signFraud = SignatureUtil.signData(object,signatureFraud);
        // Verification du signé
        // La clé est mauvaise.
        assertFalse(SignatureUtil.verifyDataSignature(object,sign, keyPairFraud.getPublic()));
        // La signature est mauvaise.
        assertFalse(SignatureUtil.verifyDataSignature(object,signFraud, pubKey));
        // La signature et la clé sont bonnes.
        assertTrue(SignatureUtil.verifyDataSignature(object,sign,pubKey ));
    }


    //TXT
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




}
