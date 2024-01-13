package com.projetenchere.common.Utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class CertificatSSLFile {

    public static void copyJksFromInputStream(String destinationPath) throws IOException {
        Path destination = Path.of(destinationPath);
        InputStream inputStream = ClassLoader.getSystemResourceAsStream("ssl/keystore.jks");

        File destinationDirectory = destination.toFile().getParentFile();
        if (!destinationDirectory.exists()) {
            if (destinationDirectory.mkdirs()) {
            } else {
                System.err.println("Échec de la création du dossier de destination : " + destinationDirectory);
            }
        }

        if (!destination.toFile().exists()) {
            Files.copy(inputStream, destination, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Copie de la configuration JKS réussie vers " + destinationPath);
        }
    }
}
