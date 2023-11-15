package com.projetenchere.common.Models.Network.Communication;

import com.projetenchere.common.Models.Network.NetworkContactInformation;

import java.security.PublicKey;

public class SecurityInformations {
    private final String id;
    private final NetworkContactInformation networkContactInformation;
    private final PublicKey publicKey;


    public SecurityInformations(String id, NetworkContactInformation networkContactInformation, PublicKey publicKey) {
        this.id = id;
        this.networkContactInformation = networkContactInformation;
        this.publicKey = publicKey;
    }

    public SecurityInformations(NetworkContactInformation networkContactInformation) {
        this.id = null;
        this.networkContactInformation = networkContactInformation;
        this.publicKey = null;
    }

    public String getId() {
        return id;
    }

    public NetworkContactInformation getNetworkContactInformation() {
        return networkContactInformation;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }
}
