package com.projetenchere.common;

import com.projetenchere.common.Models.Network.Sendable.DataWrapper;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.*;
import java.net.SocketException;

public class ServerSocketCommunicator extends SocketCommunicator{

    private SSLServerSocket mySocket;

    protected ServerSocketCommunicator(Party party) throws SocketException {
        super(party);
        try {
            SSLServerSocketFactory sslServerSocketFactory = sslContext.getServerSocketFactory();
            this.mySocket = (SSLServerSocket) sslServerSocketFactory.createServerSocket(party.getIpAddress().getPort());
            socket = (SSLSocket) mySocket.accept();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
