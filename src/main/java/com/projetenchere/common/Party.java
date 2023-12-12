package com.projetenchere.common;

import java.net.InetAddress;
import java.net.InetSocketAddress;

public class Party {
    private final InetSocketAddress ipAddress;


    public Party(InetSocketAddress ipAddress) {
        this.ipAddress = ipAddress;
    }

    public InetSocketAddress getIpAddress() {
        return ipAddress;
    }

}
