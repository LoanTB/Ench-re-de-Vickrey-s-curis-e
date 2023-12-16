package com.projetenchere.common.network;

import javax.net.ssl.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.security.KeyStore;

public class SocketFactory {
    //TODO: gérer les exceptions correctement
    protected SSLContext sslContext;

    public SocketFactory() {
        try {
            char[] password = "\";oW+~E8T65DKiZny{hAD?~kH-e;:{E)*n?U:lUv6MOPnEc/l[k5tQ')8O48YGsJI\"".toCharArray(); // Keystore password
            KeyStore keyStore = KeyStore.getInstance("JKS");
            FileInputStream keyStoreFile = new FileInputStream("keystore.jks");
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

    public SSLServerSocket createServerSocket(int port) {
        try {
            SSLServerSocketFactory sslServerSocketFactory = sslContext.getServerSocketFactory();
            return (SSLServerSocket) sslServerSocketFactory.createServerSocket(port);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public SSLSocket createSocket(InetSocketAddress address) {
        try {
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            return (SSLSocket) sslSocketFactory.createSocket(address.getAddress(), address.getPort());
        } catch (IOException e) {
            throw new RuntimeException("Could not create socket");
        }
    }


}
