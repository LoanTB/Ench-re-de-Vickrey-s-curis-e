package com.projetenchere.common.Utils;

import com.projetenchere.common.Model.Sendable.ObjectSender;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;
import java.security.Signature;

import static org.junit.jupiter.api.Assertions.*;

public class SignatureUtilsTest {
    @Test
    public void test_simple_signature_of_an_ObjectSender() throws Exception {
        // Création de l'objet à envoyer
        ObjectSender objectSender = new ObjectSender("127.0.0.1",50000,"Hello World", String.class);
        // Création des clés RSA
        KeyPair keyPair = EncryptionUtils.generateKeyPair();
        // Création des clés RSA frauduleuse
        KeyPair keyPairFraud = EncryptionUtils.generateKeyPair();
        // Création de l'outil de signature du signant
        Signature signature = SignatureUtils.initSignatureForSigning(keyPair.getPrivate());
        // Création de l'outil de signature frauduleux
        Signature signatureFraud = SignatureUtils.initSignatureForSigning(keyPairFraud.getPrivate());
        // Récupération de la signature de l'objet à envoyer
        byte[] sign = SignatureUtils.signData(objectSender.getBytes(),signature);
        // Récupération de la signature de l'objet frauduleux à envoyer
        byte[] signFraud = SignatureUtils.signData(objectSender.getBytes(),signatureFraud);
        // Verification du signé
        // La clé est mauvaise.
        assertFalse(SignatureUtils.verifyDataSignature(objectSender.getBytes(),sign, keyPairFraud.getPublic()));
        // La signature est mauvaise.
        assertFalse(SignatureUtils.verifyDataSignature(objectSender.getBytes(),signFraud, keyPair.getPublic()));
        // La signature et la clé sont bonnes.
        assertTrue(SignatureUtils.verifyDataSignature(objectSender.getBytes(),sign, keyPair.getPublic()));

    }
}
