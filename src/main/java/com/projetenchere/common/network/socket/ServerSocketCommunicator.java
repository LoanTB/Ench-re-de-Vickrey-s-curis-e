package com.projetenchere.common.network.socket;

import com.projetenchere.common.network.*;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.net.SocketException;

public class ServerSocketCommunicator extends SocketCommunicator implements IServerCommunicator {

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

    @Override
    public void reply(NetworkDataHeaders incoming, DataWrapper<?> dataOut) {
        NetworkData request = receiveFromParty();
        if (request.checkHeader(incoming)) {
            sendDataToParty(dataOut);
        }
    }
}
