package com.projetenchere.common;

import java.net.InetAddress;

public class Party {
    private final InetAddress ipAddress;
    private final Integer port;


    public Party(InetAddress ipAddress, Integer port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }


    public InetAddress getIpAddress() {
        return ipAddress;
    }

    public Integer getPort() {
        return port;
    }

}
