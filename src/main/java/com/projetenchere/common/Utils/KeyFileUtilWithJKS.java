package com.projetenchere.common.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class KeyFileUtilWithJKS implements I_KeyFileUtil {
    private static final String KEYSTORE_ALIAS = "SecureWin";
    private static final String KEYSTORE_TYPE = "JKS";
    private static final String CERTIFICATE_TYPE = "X.509";
    private static final String KEY_ALGORITHM = "RSA";
    private static final int KEY_SIZE = 2048;
    private static final int VALIDITY_DAYS = 365;
    private static final String KEYSTORE_PASSWORD = "SecureWinPaulLoanYukiRemiKatia";
    private static String KEYSTORE_FILEPATH;
    private static String CERT_FILEPATH;


    //TODO : Penser à changer les Sysout et SysErr.

    public KeyFileUtilWithJKS() {
        String OS = System.getProperty("os.name").toLowerCase();
        String configPath = "";
        String userHome = System.getProperty("user.home");

        if (OS.contains("nix") || OS.contains("nux") || OS.contains("aix")) {
            configPath = userHome + "/.config/securewin";
            KEYSTORE_FILEPATH = configPath + "/config_signature_keypair.jks";
            CERT_FILEPATH = configPath + "/config_signature_certificat.cer";
        } else if (OS.contains("windows")) {
            configPath = userHome + "/.config/securewin";
            KEYSTORE_FILEPATH = configPath + "/config_signature_keypair.jks";
            CERT_FILEPATH = configPath + "/config_signature_certificat.cer";
        } else {
            System.err.println("Système non prix en charge : " + OS);
        }

        File directoryConfig = new File(configPath);
        if (!directoryConfig.exists()) {
            if (directoryConfig.mkdirs()) {
                //System.out.println("Dossier de configuration créé avec succès : " + configPath);
            } else {
                //System.err.println("Echec de la creation du dossier de config : " +  configPath);
            }
        } else {
            //System.out.println("Dossier de configuration déjà existant : " +  configPath);
        }
    }


    public static boolean executeCommand(String command) throws IOException, InterruptedException {

        ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", command);
        Process process = processBuilder.start();
        int exitCode = process.waitFor();
        return exitCode == 0;
    }

    @Override
    public void generateAndSaveKeyPair() {
        try {
            try {
                // Commande pour générer une paire de clés dans .jks
                String genKeyCommand = "keytool -genkeypair -alias " + KEYSTORE_ALIAS + " -keyalg " + KEY_ALGORITHM + " -keysize " + KEY_SIZE + " -keystore " + KEYSTORE_FILEPATH + " -validity " + VALIDITY_DAYS + " -dname \"CN=Secure, OU=Win, O=SecureWin, L=Montpellier, ST=Occitanie, C=FR\" -storepass " + KEYSTORE_PASSWORD + " -keypass " + KEYSTORE_PASSWORD;
                //System.out.println("Generation du keystore en cours ...");
                //System.out.println(genKeyCommand);
                boolean result = executeCommand(genKeyCommand);
                if (result) {
                    // Commande pour exporter le certificat associé au .jks
                    String exportCertCommand = "keytool -export -alias " + KEYSTORE_ALIAS + " -file " + CERT_FILEPATH + " -keystore " + KEYSTORE_FILEPATH + " -storepass " + KEYSTORE_PASSWORD + " -keypass " + KEYSTORE_PASSWORD;
                    //System.out.println("Exportation du certificat en cours ...");
                    //System.out.println(exportCertCommand);

                    result = executeCommand(exportCertCommand);
                    if (result) {
                        System.out.println("Commande de generation de certificat reussie !"); //TODO : retourner true pour que l'UI affiche le message.
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isKeyPairSaved() {
        try {
            File keyStoreFile = new File(KEYSTORE_FILEPATH);
            if (!keyStoreFile.exists()) {
                return false;
            }
            File certificateFIle = new File(CERT_FILEPATH);
            if (!certificateFIle.exists()) {
                return false;
            }

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
        KeyStore keyStore = KeyStore.getInstance(KEYSTORE_TYPE);
        FileInputStream fis = new FileInputStream(KEYSTORE_FILEPATH);
        keyStore.load(fis, KEYSTORE_PASSWORD.toCharArray());
        PrivateKey privateKey = (PrivateKey) keyStore.getKey(KEYSTORE_ALIAS, KEYSTORE_PASSWORD.toCharArray());
        fis.close();
        return privateKey;
    }

    @Override
    public PublicKey getPublicKeyFromFile() throws Exception {
        FileInputStream fis = new FileInputStream(CERT_FILEPATH);
        CertificateFactory cf = CertificateFactory.getInstance(CERTIFICATE_TYPE);
        X509Certificate certificate = (X509Certificate) cf.generateCertificate(fis);
        PublicKey pubKey = certificate.getPublicKey();
        fis.close();
        return pubKey;
    }

}
