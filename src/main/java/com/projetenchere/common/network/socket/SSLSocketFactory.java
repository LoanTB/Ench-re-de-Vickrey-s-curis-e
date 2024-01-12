package com.projetenchere.common.network.socket;

import javax.net.ssl.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.security.KeyStore;

import static com.projetenchere.common.Utils.CertificatSSLFile.copyJksFromInputStream;


public class SSLSocketFactory implements ISocketFactory {
    //TODO: gérer les exceptions correctement
    protected SSLContext sslContext;

    public SSLSocketFactory() {
        try {
            char[] password = ";oW+~E8T65DKiZny{hAD?~kH-e;:{E)*n?U:lUv6MOPnEc/l[k5tQ')8O48YGsJI".toCharArray(); // Keystore password
            KeyStore keyStore = KeyStore.getInstance("JKS");

            try {
                // Définir le chemin du fichier de destination
                String destinationPath = System.getProperty("user.home") + "/.config/securewin/managerIp.txt";
                File f = new File(destinationPath);
                if (!f.exists()) {
                    // Créer le fichier et écrire l'adresse IP
                    if (!f.createNewFile()) throw new IOException();
                    System.out.println("dansleif");
                    FileWriter writer = new FileWriter(f);
                    writer.write("127.0.0.1");
                    writer.close();
                }

                System.out.println("Fichier managerIp.txt créé avec succès à l'emplacement : " + destinationPath);
            } catch (IOException e) {
                throw new RuntimeException("Une erreur s'est produite lors de la création du fichier managerIp.txt.", e);
            }

            String sourcePath = "src/main/java/ressources/ssl/keystore.jks";
            String destinationPath = System.getProperty("user.home") + "/.config/securewin/ssl/keystore.jks";
            try {
                copyJksFromInputStream(destinationPath);
                System.out.println("Copie réussie de " + sourcePath + " vers " + destinationPath);
            } catch (Exception e) {
                e.printStackTrace();
            }

            FileInputStream keyStoreFile = new FileInputStream(destinationPath);
            keyStore.load(keyStoreFile, password);
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
            keyManagerFactory.init(keyStore, password);
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
            trustManagerFactory.init(keyStore);
            this.sslContext = SSLContext.getInstance("SSL");
            sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public SSLServerSocket createServerSocket(int port) {
        try {
            SSLServerSocketFactory sslServerSocketFactory = sslContext.getServerSocketFactory();
            return (SSLServerSocket) sslServerSocketFactory.createServerSocket(port);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SSLSocket createSocket(InetSocketAddress address) {
        try {
            javax.net.ssl.SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            return (SSLSocket) sslSocketFactory.createSocket(address.getAddress(), address.getPort());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
