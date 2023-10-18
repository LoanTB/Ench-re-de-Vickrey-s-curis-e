package com.projetenchere.Util;

import com.projetenchere.common.Util.EncryptionUtil;
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

        String message = "Oui j'ai fait un test alors que j'aime pas ça";
        System.out.println("Message d'origine : "+message);
        byte[] encrypted_message = EncryptionUtil.encrypt(message, manager_publicKey);
        System.out.print("Message chiffré : ");
        System.out.println(new String(encrypted_message, 0));
        String decrypted_message_UC = EncryptionUtil.decrypt(encrypted_message,manager_privateKey);
        System.out.println("Message déchiffré : "+message);

        assertEquals(message, decrypted_message_UC);
    }
}
