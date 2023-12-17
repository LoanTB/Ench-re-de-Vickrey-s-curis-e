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
    private static final String KEYSTORE_ALIAS = "SecureWin";
    private static final String KEYSTORE_TYPE = "JKS";
    private static final String CERTIFICATE_TYPE = "X.509";
    private static final String KEY_ALGORITHM = "RSA";
    private static final int KEY_SIZE = 2048;
    private static final int VALIDITY_DAYS = 365;
    private static final String KEYSTORE_FILEPATH = "src/main/resources/config/config_signature_keypair.jks";
    private static final  String CERT_FILEPATH = "src/main/resources/config/config_signature_certificat.cer"; 
    private static final String KEYSTORE_PASSWORD = "SecureWinPaulLoanYukiRemiKatia";
    private static final String PRIVKEY_ALIAS = "privateKeySecureWin"; 
    private static final String PUBKEY_ALIAS = "publicKeySecureWin"; 


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
            // Commande pour générer une paire de clés dans .jks
            String genKeyCommand = "keytool -genkeypair -alias "+ KEYSTORE_ALIAS +" -keyalg "+KEY_ALGORITHM+" -keysize "+KEY_SIZE+" -keystore "+KEYSTORE_FILEPATH+" -validity "+VALIDITY_DAYS+" -dname \"CN=Secure, OU=Win, O=SecureWin, L=Montpellier, ST=Occitanie, C=FR\" -storepass "+KEYSTORE_PASSWORD+" -keypass "+KEYSTORE_PASSWORD;
            executeCommand(genKeyCommand);

            // Commande pour exporter le certificat associé au .jks
            String exportCertCommand = "keytool -export -alias "+ KEYSTORE_ALIAS +" -file "+CERT_FILEPATH+" -keystore "+KEYSTORE_FILEPATH+" -storepass "+KEYSTORE_PASSWORD+" -keypass "+KEYSTORE_PASSWORD;
            executeCommand(exportCertCommand);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveKeyPair(){
            try {
                
                generateCertificateByCommand();

                // Charger le certificat depuis un fichier
                FileInputStream fis = new FileInputStream(CERT_FILEPATH);
                CertificateFactory cf = CertificateFactory.getInstance(CERTIFICATE_TYPE);
                Certificate certificateChain[] = new Certificate[1];
                certificateChain[0] = cf.generateCertificate(fis);
                fis.close();

                // Charger le keystore
                KeyStore keystore = KeyStore.getInstance(KEYSTORE_TYPE);
                keystore.load(new FileInputStream(KEYSTORE_FILEPATH), KEYSTORE_PASSWORD.toCharArray());

                // Ajouter le certificat au KeyStore avec un alias spécifique
                keystore.setKeyEntry(PRIVKEY_ALIAS,getPrivateKey(KEYSTORE_FILEPATH, KEYSTORE_PASSWORD),KEYSTORE_PASSWORD.toCharArray(),certificateChain);

                // Sauvegarder le KeyStore mis à jour dans un fichier (si nécessaire)
                keystore.store(new FileOutputStream(KEYSTORE_FILEPATH), KEYSTORE_PASSWORD.toCharArray());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }




    @Override
    public boolean isKeyPairSaved(){
        try {
            KeyStore keyStore = KeyStore.getInstance(KEYSTORE_TYPE);
            keyStore.load(new FileInputStream(KEYSTORE_FILEPATH), KEYSTORE_PASSWORD.toCharArray());
            return keyStore.containsAlias(PRIVKEY_ALIAS);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public PrivateKey getPrivateKeyFromFile() throws Exception {
        KeyStore keyStore = KeyStore.getInstance(KEYSTORE_TYPE);
        keyStore.load(new FileInputStream(KEYSTORE_FILEPATH), KEYSTORE_PASSWORD.toCharArray());

        return (PrivateKey) keyStore.getKey(PRIVKEY_ALIAS, KEYSTORE_PASSWORD.toCharArray());
    }

    @Override
    public PublicKey getPublicKeyFromFile() throws Exception {
        KeyStore keyStore = KeyStore.getInstance(KEYSTORE_TYPE);
        keyStore.load(new FileInputStream(KEYSTORE_FILEPATH), KEYSTORE_PASSWORD.toCharArray());

        Certificate cert = keyStore.getCertificate();
        return cert.getPublicKey();
    }

}
