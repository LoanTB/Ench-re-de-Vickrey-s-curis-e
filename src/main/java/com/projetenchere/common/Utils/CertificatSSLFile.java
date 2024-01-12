package com.projetenchere.common.Utils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class CertificatSSLFile {
    /*
            // Spécifiez le chemin du fichier source et de destination
        String sourcePath = "/chemin/vers/source.jks";
        String destinationPath = "/chemin/vers/destination.jks";

        try {
            copyJks(sourcePath, destinationPath);
            System.out.println("Copie réussie de " + sourcePath + " vers " + destinationPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    */
    public static void copyJks(String sourcePath, String destinationPath) throws IOException {
        Path source = Path.of(sourcePath);
        Path destination = Path.of(destinationPath);

        Files.copy(source,destination, StandardCopyOption.REPLACE_EXISTING);
    }
}
