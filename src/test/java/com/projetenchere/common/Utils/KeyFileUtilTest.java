package com.projetenchere.common.Utils;

import com.projetenchere.common.Utils.KeyFileUtil;
import org.junit.jupiter.api.Test;

import java.security.*;

import static org.junit.jupiter.api.Assertions.*;

public class KeyFileUtilTest {

    @Test
    public void testSaveAndGetKeyPair() {
        try {
            // Générer une nouvelle paire de clés

            KeyPair a = EncryptionUtil .generateKeyPair();

            // Enregistrer la paire de clés dans un fichier .jks
            KeyFileUtil.saveKeyPair(a);

            // Vérifier si la paire de clés a été enregistrée dans le fichier .jks
            assertTrue(KeyFileUtil.isKeyPairSaved());

            // Récupérer la clé privée du fichier .jks et vérifier si elle correspond à celle générée
            PrivateKey privateKeyFromKeyStore = KeyFileUtil.getPrivateKeyFromFile();
            assertEquals(a.getPrivate(), privateKeyFromKeyStore);

            // Récupérer la clé publique du fichier .jks et vérifier si elle correspond à celle générée
            PublicKey publicKeyFromKeyStore = KeyFileUtil.getPublicKeyFromFile();
            assertEquals(a.getPublic(), publicKeyFromKeyStore);

        } catch (Exception e) {
            fail("Exception: " + e.getMessage());
        }
    }
}
