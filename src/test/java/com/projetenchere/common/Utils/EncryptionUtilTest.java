package com.projetenchere.common.Utils;

import org.junit.jupiter.api.Test;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

public class EncryptionUtilTest {
    @Test
    public void test_String_data_retention_during_encryption() throws Exception {
        KeyPair managerKeys = EncryptionUtil.generateKeyPair();
        PrivateKey manager_privateKey = managerKeys.getPrivate();
        PublicKey manager_publicKey = managerKeys.getPublic();

        String manager_privKey_str = Base64.getEncoder().encodeToString((manager_privateKey.getEncoded()));
        String manager_pubKey_str = Base64.getEncoder().encodeToString((manager_publicKey.getEncoded()));
        System.out.println("Clé privée : "+ manager_privKey_str);
        System.out.println("Clé publique : "+ manager_pubKey_str);

        double price = 12.34;
        System.out.println("Prix d'origine : "+price);
        byte[] encryptedPrice = EncryptionUtil.encrypt(price, manager_publicKey);
        System.out.print("Message chiffré : ");
        System.out.println(new String(encryptedPrice, 0));
        double decryptedPrice = EncryptionUtil.decrypt(encryptedPrice,manager_privateKey);
        System.out.println("Prix déchiffré : "+decryptedPrice);

        assertEquals(price, decryptedPrice);
    }
}
