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
import java.util.Objects;

public class KeyFileUtilWithJKS implements I_KeyFileUtil {
    private static final String KEYSTORE_ALIAS = "SecureWin";
    private static final String KEYSTORE_TYPE = "JKS";
    private static final String CERTIFICATE_TYPE = "X.509";
    private static final String KEY_ALGORITHM = "RSA";
    private static final int KEY_SIZE = 2048;
    private static final int VALIDITY_DAYS = 365;
    private static final String KEYSTORE_FILENAME = "config_signature_keypair.jks";
    private static final  String CERT_FILENAME = "config_signature_certificat.cer";
    private static final String KEYSTORE_PASSWORD = "SecureWinPaulLoanYukiRemiKatia";

    private static void executeCommand(String command) throws IOException {
        Process process = Runtime.getRuntime().exec(command);
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        reader.close();
    }

    private String getFilePath(String fileName){
        File ressources = new File("");

        File configFile = new File(ressources.getAbsolutePath()+"/src/main/resources/config/"+fileName);

        String filepath = configFile.getAbsolutePath();
        System.out.println(filepath);
        return filepath;
    }

    @Override
    public void generateAndSaveKeyPair(){
        try {
            try {

                String KEYSTORE_FILEPATH = getFilePath(KEYSTORE_FILENAME);

                String CERT_FILEPATH =  getFilePath(CERT_FILENAME);

                // Commande pour générer une paire de clés dans .jks
                String genKeyCommand = "keytool -genkeypair -alias "+ KEYSTORE_ALIAS +" -keyalg "+KEY_ALGORITHM+" -keysize "+KEY_SIZE+" -keystore "+KEYSTORE_FILEPATH+" -validity "+VALIDITY_DAYS+" -dname \"CN=Secure, OU=Win, O=SecureWin, L=Montpellier, ST=Occitanie, C=FR\" -storepass "+KEYSTORE_PASSWORD+" -keypass "+KEYSTORE_PASSWORD;
                System.out.println(genKeyCommand);
                executeCommand(genKeyCommand);
                System.out.println("Commande de generation de keystore reussie !");

                // Commande pour exporter le certificat associé au .jks
                String exportCertCommand = "keytool -export -alias "+ KEYSTORE_ALIAS +" -file "+CERT_FILEPATH+" -keystore "+KEYSTORE_FILEPATH+" -storepass "+KEYSTORE_PASSWORD+" -keypass "+KEYSTORE_PASSWORD;
                System.out.println(exportCertCommand);

                executeCommand(exportCertCommand);
                System.out.println("Commande de generation de certificat reussie !");


            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isKeyPairSaved(){
        try {

            String KEYSTORE_FILEPATH = getFilePath(KEYSTORE_FILENAME);
            String CERT_FILEPATH =  getFilePath(CERT_FILENAME);

            KeyStore keyStore = KeyStore.getInstance(KEYSTORE_TYPE);
            keyStore.load(new FileInputStream(KEYSTORE_FILEPATH), KEYSTORE_PASSWORD.toCharArray());
            return keyStore.containsAlias(KEYSTORE_ALIAS);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public PrivateKey getPrivateKeyFromFile() throws Exception {

        String KEYSTORE_FILEPATH = getFilePath(KEYSTORE_FILENAME);

        KeyStore keyStore = KeyStore.getInstance(KEYSTORE_TYPE);
        FileInputStream fis = new FileInputStream(KEYSTORE_FILEPATH);
        keyStore.load(fis, KEYSTORE_PASSWORD.toCharArray());
        PrivateKey privateKey = (PrivateKey) keyStore.getKey(KEYSTORE_ALIAS, KEYSTORE_PASSWORD.toCharArray());
        fis.close();
        return privateKey;
    }

    @Override
    public PublicKey getPublicKeyFromFile() throws Exception {

        String CERT_FILEPATH =  getFilePath(CERT_FILENAME);

        FileInputStream fis = new FileInputStream(CERT_FILEPATH);
        CertificateFactory cf = CertificateFactory.getInstance(CERTIFICATE_TYPE);
        X509Certificate certificate = (X509Certificate) cf.generateCertificate(fis);
        PublicKey pubKey = certificate.getPublicKey();
        fis.close();
        return pubKey;
    }

}
