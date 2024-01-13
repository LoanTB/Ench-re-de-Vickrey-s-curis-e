package com.projetenchere.common.Utils.stub;

import com.projetenchere.common.Utils.EncryptionUtil;
import com.projetenchere.common.Utils.I_KeyFileUtil;

import java.io.*;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class KeyFileUtilWithTXT implements I_KeyFileUtil {

    private static String FILE_PUBLIC_KEY;
    private static String FILE_PRIVATE_KEY;

    public KeyFileUtilWithTXT() {
        String OS = System.getProperty("os.name").toLowerCase();
        String configPath = "";
        String userHome = System.getProperty("user.home");

        if (OS.contains("win")) {
            configPath = "C:\\Users\\Utilisateur\\AppData\\Local\\securewin";
            FILE_PUBLIC_KEY = configPath + "\\cle_publique.txt";
            FILE_PRIVATE_KEY = configPath + "\\cle_privee.txt";
        } else if (OS.contains("nix") || OS.contains("nux") || OS.contains("aix")) {
            configPath = userHome + "/.config/securewin";
            FILE_PUBLIC_KEY = configPath + "/cle_publique.txt\"";
            FILE_PRIVATE_KEY = configPath + "/cle_privee.txt";
        } else if (OS.contains("mac")) {
            configPath = userHome + "/Library/Application Support/securewin";
            FILE_PUBLIC_KEY = configPath + "/cle_publique.txt";
            FILE_PRIVATE_KEY = configPath + "/cle_privee.txt";

        } else {
            System.err.println("Système non prix en charge !");
        }

        File directoryConfig = new File(configPath);
        if (!directoryConfig.exists()) {
            if (directoryConfig.mkdirs()) {

            } else {
                System.err.println("Echec de la creation du dossier de config : " + configPath);
            }
        }
    }

    public void generateAndSaveKeyPair() {
        try {
            KeyPair keyPair = EncryptionUtil.generateKeyPair();
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            File ressources = new File("");
            System.out.println(ressources.getAbsolutePath());

            File publicKeyFile = new File(ressources.getAbsolutePath() + "/src/main/resources/config/" + FILE_PUBLIC_KEY);
            if (!publicKeyFile.exists()) {
                publicKeyFile.createNewFile(); // Créer le fichier s'il n'existe pas
            }
            ObjectOutputStream clePubliqueOut = new ObjectOutputStream(new FileOutputStream(publicKeyFile));
            clePubliqueOut.writeObject(publicKey);
            clePubliqueOut.close();

            File privateKeyFile = new File(ressources.getAbsolutePath() + "/src/main/resources/config/" + FILE_PRIVATE_KEY);
            if (!privateKeyFile.exists()) {
                privateKeyFile.createNewFile(); // Créer le fichier s'il n'existe pas
            }
            // Stockage de la clé privée dans le fichier
            ObjectOutputStream clePriveeOut = new ObjectOutputStream(new FileOutputStream(privateKeyFile));
            clePriveeOut.writeObject(privateKey);
            clePriveeOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isKeyPairSaved() {
        File publicKeyFile = new File(FILE_PUBLIC_KEY);
        File privateKeyFile = new File(FILE_PRIVATE_KEY);
        return publicKeyFile.exists() && privateKeyFile.exists();
    }

    @Override
    public PrivateKey getPrivateKeyFromFile() throws Exception {
        InputStream is = ClassLoader.getSystemResourceAsStream(FILE_PRIVATE_KEY);
        ObjectInputStream privateKeyIn = new ObjectInputStream(is);
        PrivateKey privateKey = (PrivateKey) privateKeyIn.readObject();
        privateKeyIn.close();
        return privateKey;
    }

    @Override
    public PublicKey getPublicKeyFromFile() throws Exception {
        InputStream is = ClassLoader.getSystemResourceAsStream(FILE_PUBLIC_KEY);
        ObjectInputStream publicKeyIn = new ObjectInputStream(is);
        PublicKey publicKey = (PublicKey) publicKeyIn.readObject();
        publicKeyIn.close();

        return publicKey;
    }
}