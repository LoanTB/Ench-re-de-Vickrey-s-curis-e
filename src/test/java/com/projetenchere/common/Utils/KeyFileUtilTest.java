package com.projetenchere.common.Utils;

import org.junit.jupiter.api.Test;

import java.security.*;

import static org.junit.jupiter.api.Assertions.*;

public class KeyFileUtilTest {

    @Test
    public void testSaveAndGetKeyPairWithJKS() {
        try {
            // Générer une nouvelle paire de clés
            I_KeyFileUtil keyFile = new KeyFileUtilWithJKS();

            KeyPair a = EncryptionUtil .generateKeyPair();

            // Enregistrer la paire de clés dans un fichier .jks
            keyFile.saveKeyPair(a);

            // Vérifier si la paire de clés a été enregistrée dans le fichier .jks
            assertTrue(keyFile.isKeyPairSaved());

            // Récupérer la clé privée du fichier .jks et vérifier si elle correspond à celle générée
            PrivateKey privateKeyFromKeyStore = keyFile.getPrivateKeyFromFile();
            assertEquals(a.getPrivate(), privateKeyFromKeyStore);

            // Récupérer la clé publique du fichier .jks et vérifier si elle correspond à celle générée
            PublicKey publicKeyFromKeyStore = keyFile.getPublicKeyFromFile();
            assertEquals(a.getPublic(), publicKeyFromKeyStore);

        } catch (Exception e) {
            fail("Exception: " + e.getMessage());
        }
    }
}
