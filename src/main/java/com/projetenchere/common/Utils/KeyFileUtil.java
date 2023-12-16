package com.projetenchere.common.Utils;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class KeyFileUtil {
    private static final String KEYSTORE_FILE = "projet-enchere/src/main/resources/config/config_signature_keypair.jks";
    private static final String KEYSTORE_PASSWORD = "SecureWinPaulLoanYukiRemiKatia";
    private static final String KEY_ALIAS = "certificateSecureWin"; //Une sorte d'identifiant du certificat.
    private static final String KEY_PASSWORD = "key_password";

    public static void saveKeyPair(KeyPair keyPair) {
        try {
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(null, null);

/*
            X509Certificate[] certificateChain = new X509Certificate[1];
            certificateChain[0] = ;
*/
            //keyStore.setKeyEntry(KEY_ALIAS, keyPair.getPrivate(), KEY_PASSWORD.toCharArray(), ); Il faut générer un certificat à partir de la clé publique ! Hors c'est galère
            FileOutputStream fos = new FileOutputStream(KEYSTORE_FILE);


            keyStore.store(fos, KEYSTORE_PASSWORD.toCharArray());
            fos.close();
            System.out.println("La paire de clés a été sauvegardée avec succès.");
        }catch (KeyStoreException e){
            System.err.println("Erreur KeyStore : Impossible d'obtenir l'instance KeyStore.");
            e.printStackTrace();
        }catch (CertificateException e){
            System.err.println("Erreur de certificat :Problème avec le certificat.");
            e.printStackTrace();
        }catch (NoSuchAlgorithmException e){
            System.err.println("Erreur d'algorithme : Algorithme non pris en charge.");
            e.printStackTrace();
        }catch (IOException e){
            System.err.println("Erreur d'entrée/sortie : Impossible de lire/écrire dans le fichier.");
            e.printStackTrace();
        }catch (Exception e){
            System.err.println("Une erreur inattendue s'est produite.");
            e.printStackTrace();
        }
    }

    public static boolean isKeyPairSaved(){
        try {
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(new FileInputStream(KEYSTORE_FILE), KEYSTORE_PASSWORD.toCharArray());
            return keyStore.containsAlias(KEY_ALIAS);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static PrivateKey getPrivateKeyFromFile() throws Exception {
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(new FileInputStream(KEYSTORE_FILE), KEYSTORE_PASSWORD.toCharArray());

        return (PrivateKey) keyStore.getKey(KEY_ALIAS, KEY_PASSWORD.toCharArray());
    }

    public static PublicKey getPublicKeyFromFile() throws Exception {
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(new FileInputStream(KEYSTORE_FILE), KEYSTORE_PASSWORD.toCharArray());

        Certificate cert = keyStore.getCertificate(KEY_ALIAS);
        return cert.getPublicKey();
    }

}

