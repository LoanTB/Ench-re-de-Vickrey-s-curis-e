package com.projetenchere.common;

import com.projetenchere.common.Models.Network.Sendable.DataWrapper;

import javax.net.ssl.*;
import java.io.*;
import java.net.SocketException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public abstract class SocketCommunicator implements ICommunicator{
    //TODO: gérer les exceptions correctement
    protected Party party;
    protected SSLContext sslContext;
    protected SSLSocket socket;


    protected SocketCommunicator(Party party) throws SocketException {
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
    @Override
    public void sendDataToParty(Party party, DataWrapper<? extends Serializable> data) {
        try {
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public  DataWrapper<? extends Serializable> waitForDataFromParty(Party party, IDataHandler handler) {
        DataWrapper<? extends Serializable> data;
        try {
            InputStream inputStream  = socket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            data = (DataWrapper<? extends Serializable>) objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            data = null;
        }
        return data;
    }


}
