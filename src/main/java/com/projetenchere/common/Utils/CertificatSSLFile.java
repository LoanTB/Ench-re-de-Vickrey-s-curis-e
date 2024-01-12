package com.projetenchere.common.Utils;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class CertificatSSLFile {

    public static void main(String[] args) {
        // Spécifiez le chemin du fichier source et de destination
        String sourcePath = "/chemin/vers/source.jks";
        String destinationPath = "/chemin/vers/destination.jks";

        try {
            copyJks(sourcePath, destinationPath);
            System.out.println("Copie réussie de " + sourcePath + " vers " + destinationPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void copyJks(String sourcePath, String destinationPath) throws Exception {
        Path source = Path.of(sourcePath);
        Path destination = Path.of(destinationPath);

        // Utilisez Files.copy pour copier le fichier avec ARM (Automatic Resource Management)
        try (var inputStream = Files.newInputStream(source);
             var outputStream = Files.newOutputStream(destination)) {

            Files.copy(inputStream, destination, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
