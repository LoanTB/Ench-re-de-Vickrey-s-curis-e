package com.projetenchere.common.Utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;

public class CertificatSSLFile {

    public static void copyJksFromInputStream(String destinationPath) throws IOException {
        Path destination = Path.of(destinationPath);
        InputStream inputStream = ClassLoader.getSystemResourceAsStream("ssl/keystore.jks");

        // Vérifier si le répertoire de destination existe, sinon le créer
        File destinationDirectory = destination.toFile().getParentFile();
        if (!destinationDirectory.exists()) {
            if (destinationDirectory.mkdirs()) {
                System.out.println("Dossier de destination créé avec succès : " + destinationDirectory);
            } else {
                System.err.println("Échec de la création du dossier de destination : " + destinationDirectory);
            }
        } else {
            System.out.println("Dossier de destination déjà existant : " + destinationDirectory);
        }

        // Copier le fichier JKS vers le dossier de destination
        Files.copy(inputStream, destination, StandardCopyOption.REPLACE_EXISTING);

        System.out.println("Copie réussie vers " + destinationPath);
    }
}
