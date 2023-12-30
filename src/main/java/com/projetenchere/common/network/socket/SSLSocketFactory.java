package com.projetenchere.common.network.socket;

import javax.net.ssl.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.security.KeyStore;

public class SSLSocketFactory implements ISocketFactory{
    //TODO: gérer les exceptions correctement
    protected SSLContext sslContext;

    public SSLSocketFactory() {
        try {
            char[] password = ";oW+~E8T65DKiZny{hAD?~kH-e;:{E)*n?U:lUv6MOPnEc/l[k5tQ')8O48YGsJI".toCharArray(); // Keystore password
            KeyStore keyStore = KeyStore.getInstance("JKS");
            FileInputStream keyStoreFile = new FileInputStream(System.getProperty("user.home") + "/.config/securewin/ssl/keystore.jks");
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
            throw new RuntimeException();
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
