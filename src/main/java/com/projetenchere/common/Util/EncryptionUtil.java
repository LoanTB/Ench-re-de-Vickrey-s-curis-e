package com.projetenchere.common.Util;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Cipher;
import javax.crypto.*;
import java.security.*;
import java.util.*;
// Nous allons utiliser RSA
public class EncryptionUtil {
    // Générer une clé pair et la return
    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048); // Taille de la clé
        return keyPairGenerator.generateKeyPair();
    }

    // Chiffrer un string en utilisant RSA et une clé publique
    public static byte[] encrypt(String text, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");  
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(text.getBytes());
    }

    // Déchiffrer un string en utiliser RSA et une clé privée
    public static String decrypt(byte[] ciphertext, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(ciphertext);
        return new String(decryptedBytes);
    }

    // Test
    public static void main(String[] args) {
        try {
            KeyPair managerKeys = generateKeyPair();

            PrivateKey manager_privateKey = managerKeys.getPrivate();
            PublicKey manager_publicKey = managerKeys.getPublic();

            String manager_privKey_str = Base64.getEncoder().encodeToString((manager_privateKey.getEncoded()));
            String manager_pubKey_str = Base64.getEncoder().encodeToString((manager_publicKey.getEncoded()));

            System.out.println("Clé privée : "+ manager_privKey_str);
            System.out.println("Clé publique : "+ manager_pubKey_str);

        }
        catch(Exception a)
        {
            System.out.println("Erreur de génération de paire de clé");
        }
    }
}
