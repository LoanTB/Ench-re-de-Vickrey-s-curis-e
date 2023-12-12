package com.projetenchere.common;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.net.SocketException;

public class ClientSocketCommunicator extends SocketCommunicator{

    public ClientSocketCommunicator(Party party){
        super(party);
        try {
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            this.socket = (SSLSocket) sslSocketFactory.createSocket();
            socket.bind(party.getIpAddress());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
