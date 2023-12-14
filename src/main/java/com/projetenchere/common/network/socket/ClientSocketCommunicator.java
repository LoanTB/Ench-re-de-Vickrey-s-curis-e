package com.projetenchere.common.network.socket;

import com.projetenchere.common.network.*;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.Serializable;

public class ClientSocketCommunicator extends SocketCommunicator implements IClientCommunicator {

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

    @SuppressWarnings("unchecked") // this is necessary since
    public <T extends Serializable> DataWrapper<T> requestData(NetworkDataHeaders headerOut, NetworkDataHeaders wantedHeader) {
        NetworkData request = new NetworkData(headerOut);
        DataWrapper<T> data;
        sendDataToParty(request);
        do {
            try {
                data = (DataWrapper<T>) receiveFromParty();
            } catch (ClassCastException e) {
                data = new DataWrapper<>(null, NetworkDataHeaders.ERROR);
            }
        } while (data.checkHeader(wantedHeader));
        return data;
    }
}
