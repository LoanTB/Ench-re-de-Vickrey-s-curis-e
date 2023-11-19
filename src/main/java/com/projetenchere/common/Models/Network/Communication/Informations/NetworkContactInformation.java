package com.projetenchere.common.Models.Network.Communication.Informations;

import java.io.Serializable;

public record NetworkContactInformation(String ip, int port) implements Serializable {

    @Override
    public String toString() {
        return "NetworkContactInformation{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                '}';
    }
}
