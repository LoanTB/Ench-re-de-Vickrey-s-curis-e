package com.projetenchere.common.network;

import java.io.Serializable;

public class NetworkData implements Serializable {
    private final NetworkDataHeaders header;
    public NetworkData(NetworkDataHeaders header) {
        this.header = header;
    }

    public static NetworkData error() {
        return new NetworkData(NetworkDataHeaders.ERROR);
    }

    public boolean checkHeader(NetworkDataHeaders otherHeader) {
        if (this.header.equals(NetworkDataHeaders.ERROR) || otherHeader.equals(NetworkDataHeaders.ERROR) ) {
            return false;
        }
        return this.header.equals(otherHeader);
    }
}
