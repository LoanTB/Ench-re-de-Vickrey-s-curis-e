package com.projetenchere.common.Utils.stub;

import com.projetenchere.common.Utils.I_KeyFileUtil;

import java.io.*;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class KeyFileUtilWithTXT implements I_KeyFileUtil {

    private static final String FICHIER_CLE_PUBLIQUE = "projet-enchere/src/main/resources/config/cle_publique.txt";
    private static final String FICHIER_CLE_PRIVEE = "projet-enchere/src/main/resources/config/cle_privee.txt";

    @Override
    public void saveKeyPair(KeyPair keyPair) {
        PublicKey clePublique = keyPair.getPublic();
        PrivateKey clePrivee = keyPair.getPrivate();

        try {
            // Stockage de la clé publique dans un fichier
            ObjectOutputStream clePubliqueOut = new ObjectOutputStream(new FileOutputStream(FICHIER_CLE_PUBLIQUE));
            clePubliqueOut.writeObject(clePublique);
            clePubliqueOut.close();

            // Stockage de la clé privée dans un fichier
            ObjectOutputStream clePriveeOut = new ObjectOutputStream(new FileOutputStream(FICHIER_CLE_PRIVEE));
            clePriveeOut.writeObject(clePrivee);
            clePriveeOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isKeyPairSaved() {
        File fichierClePublique = new File(FICHIER_CLE_PUBLIQUE);
        File fichierClePrivee = new File(FICHIER_CLE_PRIVEE);
        return fichierClePublique.exists() && fichierClePrivee.exists();
    }

    public KeyPair getKeyPairFromFile(){
        try {
            // Récupération de la clé publique depuis le fichier
            ObjectInputStream clePubliqueIn = new ObjectInputStream(new FileInputStream(FICHIER_CLE_PUBLIQUE));
            PublicKey clePublique = (PublicKey) clePubliqueIn.readObject();
            clePubliqueIn.close();

            // Récupération de la clé privée depuis le fichier
            ObjectInputStream clePriveeIn = new ObjectInputStream(new FileInputStream(FICHIER_CLE_PRIVEE));
            PrivateKey clePrivee = (PrivateKey) clePriveeIn.readObject();
            clePriveeIn.close();

            return new KeyPair(clePublique, clePrivee);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PrivateKey getPrivateKeyFromFile() throws Exception {
        KeyPair kp = getKeyPairFromFile();
        return kp.getPrivate();
    }

    @Override
    public PublicKey getPublicKeyFromFile() throws Exception {
        KeyPair kp = getKeyPairFromFile();
        return kp.getPublic();
    }
}

/*
    public static void main(String[] args) {
        // Exemple d'utilisation :
        if (!clesStockees()) {
            // Génération des clés
            KeyPairGenerator generateur = null;
            try {
                generateur = KeyPairGenerator.getInstance("RSA");
                generateur.initialize(2048);
                KeyPair paireCles = generateur.generateKeyPair();

                // Stockage des clés
                stockerCles(paireCles);
                System.out.println("Clés stockées avec succès !");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        } else {
            // Clés déjà stockées, récupération des clés
            KeyPair cles = recupererCles();
            if (cles != null) {
                System.out.println("Clé publique : " + cles.getPublic());
                System.out.println("Clé privée : " + cles.getPrivate());
            } else {
                System.out.println("Erreur lors de la récupération des clés.");
            }
        }
    }
}
 */
