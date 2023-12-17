package com.projetenchere.common.Utils;
import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.io.FileInputStream;

public class KeyFileUtilWithJKS implements I_KeyFileUtil {
    private static final String KEYSTORE_FILEPATH = "src/main/resources/config/config_signature_keypair.jks";
    private static final  String CERT_FILEPATH = "src/main/resources/config/config_signature_certificat.cer"; // Chemin vers le fichier du certificat à ajouter
    private static final String KEYSTORE_PASSWORD = "SecureWinPaulLoanYukiRemiKatia";
    private static final String PRIVKEY_ALIAS = "privateKeySecureWin"; //Une sorte d'identifiant du certificat.
    private static final String PUBKEY_ALIAS = "publicKeySecureWin"; //Une sorte d'identifiant du certificat.


    public KeyFileUtilWithJKS(){

    }
    
    private static void executeCommand(String command) throws IOException {
        Process process = Runtime.getRuntime().exec(command);
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        reader.close();
    }
    
    public void generateCertificateByCommand(){
        try {
            // Commande pour générer une paire de clés
            String genKeyCommand = "keytool -genkeypair -alias "+ PUBKEY_ALIAS +" -keyalg RSA -keysize 2048 -keystore "+KEYSTORE_FILEPATH+" -validity 365 -dname \"CN=Secure, OU=Win, O=SecureWin, L=Montpellier, ST=Occitanie, C=FR\" -storepass "+KEYSTORE_PASSWORD+" -keypass "+KEYSTORE_PASSWORD;
            executeCommand(genKeyCommand);
            //System.out.println(genKeyCommand);
            // Commande pour exporter le certificat
            String exportCertCommand = "keytool -export -alias "+ PUBKEY_ALIAS +" -file "+CERT_FILEPATH+" -keystore "+KEYSTORE_FILEPATH+" -storepass "+KEYSTORE_PASSWORD+" -keypass "+KEYSTORE_PASSWORD;
            //System.out.println(exportCertCommand);
            executeCommand(exportCertCommand);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
/*
    @Override
    public void saveKeyPair(KeyPair keyPair) {
        try {
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(null, null);

            // Charger le certificat depuis un fichier
            File fileCertificate = new File(CERT_FILEPATH);
            if(!fileCertificate.exists()){
                generateCertificateByCommand();
            }

            FileInputStream fis = new FileInputStream(CERT_FILEPATH);
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate certificate = (X509Certificate) cf.generateCertificate(fis);
            fis.close();

            keyStore.setKeyEntry(PRIVKEY_ALIAS, keyPair.getPrivate(), KEYSTORE_PASSWORD.toCharArray(), );
            FileOutputStream fos = new FileOutputStream(KEYSTORE_FILEPATH);

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
*/
    @Override
    public void saveKeyPair(KeyPair keyPair){
            try {
                
                generateCertificateByCommand();

                // Charger le certificat depuis un fichier
                FileInputStream fis = new FileInputStream(CERT_FILEPATH);
                CertificateFactory cf = CertificateFactory.getInstance("X.509");
                Certificate certificateChain[] = new Certificate[1];
                certificateChain[0] = cf.generateCertificate(fis);
                fis.close();

                // Charger le keystore
                KeyStore keystore = KeyStore.getInstance("JKS");
                keystore.load(new FileInputStream(KEYSTORE_FILEPATH), KEYSTORE_PASSWORD.toCharArray());

                // Ajouter le certificat au KeyStore avec un alias spécifique
                keystore.setKeyEntry(PRIVKEY_ALIAS,keyPair.getPrivate(),KEYSTORE_PASSWORD.toCharArray(),certificateChain);
                keystore.setKeyEntry(PUBKEY_ALIAS,keyPair.getPublic(),KEYSTORE_PASSWORD.toCharArray(),certificateChain);

                // Sauvegarder le KeyStore mis à jour dans un fichier (si nécessaire)
                keystore.store(new FileOutputStream(KEYSTORE_FILEPATH), KEYSTORE_PASSWORD.toCharArray());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }




    @Override
    public boolean isKeyPairSaved(){
        try {
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(new FileInputStream(KEYSTORE_FILEPATH), KEYSTORE_PASSWORD.toCharArray());
            return keyStore.containsAlias(PRIVKEY_ALIAS);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public PrivateKey getPrivateKeyFromFile() throws Exception {
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(new FileInputStream(KEYSTORE_FILEPATH), KEYSTORE_PASSWORD.toCharArray());

        return (PrivateKey) keyStore.getKey(PRIVKEY_ALIAS, KEYSTORE_PASSWORD.toCharArray());
    }

    @Override
    public PublicKey getPublicKeyFromFile() throws Exception {
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(new FileInputStream(KEYSTORE_FILEPATH), KEYSTORE_PASSWORD.toCharArray());

        Certificate cert = keyStore.getCertificate(PRIVKEY_ALIAS);
        return cert.getPublicKey();
    }

}
