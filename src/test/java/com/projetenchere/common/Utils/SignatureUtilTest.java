package com.projetenchere.common.Utils;

public class SignatureUtilTest {
    /*
    @Test
    public void test_simple_signature_of_an_ObjectSender() throws Exception {
        // Création de l'objet à envoyer
        DataWrapper dataWrapper = new DataWrapper("127.0.0.1", 50000, "Hello World", String.class);
        // Création des clés RSA
        KeyPair keyPair = EncryptionUtil.generateKeyPair();
        // Création des clés RSA frauduleuse
        KeyPair keyPairFraud = EncryptionUtil.generateKeyPair();
        // Création de l'outil de signature du signant
        Signature signature = SignatureUtil.initSignatureForSigning(keyPair.getPrivate());
        // Création de l'outil de signature frauduleux
        Signature signatureFraud = SignatureUtil.initSignatureForSigning(keyPairFraud.getPrivate());
        // Récupération de la signature de l'objet à envoyer
        byte[] sign = SignatureUtil.signData(SerializationUtil.serialize(dataWrapper), signature);
        // Récupération de la signature de l'objet frauduleux à envoyer
        byte[] signFraud = SignatureUtil.signData(SerializationUtil.serialize(dataWrapper), signatureFraud);
        // Verification du signé
        // La clé est mauvaise.
        assertFalse(SignatureUtil.verifyDataSignature(SerializationUtil.serialize(dataWrapper), sign, keyPairFraud.getPublic()));
        // La signature est mauvaise.
        assertFalse(SignatureUtil.verifyDataSignature(SerializationUtil.serialize(dataWrapper), signFraud, keyPair.getPublic()));
        // La signature et la clé sont bonnes.
        assertTrue(SignatureUtil.verifyDataSignature(SerializationUtil.serialize(dataWrapper), sign, keyPair.getPublic()));
    }
     */
}
