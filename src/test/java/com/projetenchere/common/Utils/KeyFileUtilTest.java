package com.projetenchere.common.Utils;

import com.projetenchere.common.Utils.stub.KeyFileUtilWithTXT;
import org.junit.jupiter.api.Test;

import java.security.*;

import static org.junit.jupiter.api.Assertions.*;

public class KeyFileUtilTest {

    @Test
    public void testSaveAndGetKeyPairWithJKS() {
        try {
            // Générer une nouvelle paire de clés
            KeyFileUtilWithJKS keyFile = new KeyFileUtilWithJKS();

            KeyPair a = EncryptionUtil.generateKeyPair();

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

/*
    @Test
    public  void testSaveAndGetPublicKeyWithTXT() throws Exception {
        KeyFileUtilWithTXT keyFile = new KeyFileUtilWithTXT();
        // Exemple d'utilisation :

            // Génération des clés
            EncryptionUtil generateur = new EncryptionUtil();
            KeyPair keypairs = generateur.generateKeyPair();

            // Stockage des clés
            keyFile.saveKeyPair(keypairs);

            // Clés déjà stockées, récupération des clés
            PublicKey pubKey = keyFile.getPublicKeyFromFile();
            assertEquals(keypairs.getPublic(), pubKey);
    }

    @Test
    public  void testSaveAndGetPrivateKeyWithTXT() throws Exception {
        KeyFileUtilWithTXT keyFile = new KeyFileUtilWithTXT();
        // Exemple d'utilisation :

            // Génération des clés
            EncryptionUtil generateur = new EncryptionUtil();
            KeyPair keypairs = generateur.generateKeyPair();

            // Stockage des clés
            keyFile.saveKeyPair(keypairs);


        // Clés déjà stockées, récupération des clés
        PrivateKey privKey = keyFile.getPrivateKeyFromFile();

        assertEquals(keypairs.getPrivate(), privKey);
    }*/
}
