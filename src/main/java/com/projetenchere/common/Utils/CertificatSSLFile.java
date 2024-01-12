package com.projetenchere.common.Utils;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.KeyStore;

public class CertificatSSLFile {

    /*public static void main(String[] args) {
        // Spécifiez le chemin du fichier source et de destination
        String sourcePath = "/chemin/vers/source.jks";
        String destinationPath = "/chemin/vers/destination.jks";

        try {
            // Méthode 1 : Utiliser les API Java standard (Java 7 et versions ultérieures)
            copyJksUsingJava7NIO(sourcePath, destinationPath);

            // Méthode 2 : Utiliser les flux d'entrée/sortie
            // copyJksUsingStreams(sourcePath, destinationPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/

    private static void copyJksUsingJava7NIO(String sourcePath, String destinationPath) throws IOException {
        Path source = Path.of(sourcePath);
        Path destination = Path.of(destinationPath);

        // Utilisez Files.copy pour copier le fichier (Java 7 et versions ultérieures)
        Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);

        System.out.println("Copie réussie de " + sourcePath + " vers " + destinationPath);
    }

    private static void copyJksUsingStreams(String sourcePath, String destinationPath) throws IOException {
        try (InputStream inputStream = new FileInputStream(sourcePath);
             OutputStream outputStream = new FileOutputStream(destinationPath)) {

            // Créez un tampon pour stocker les données pendant la copie
            byte[] buffer = new byte[1024];
            int bytesRead;

            // Lire à partir du fichier source et écrire dans le fichier de destination
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            System.out.println("Copie réussie de " + sourcePath + " vers " + destinationPath);
        }
    }
}
