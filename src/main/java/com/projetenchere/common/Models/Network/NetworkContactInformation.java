package com.projetenchere.common.Models.Network;

public class NetworkContactInformation {
    final private String ip;
    final private int port;

    public NetworkContactInformation(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String toString() {
        return "NetworkContactInformation{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                '}';
    }
}
