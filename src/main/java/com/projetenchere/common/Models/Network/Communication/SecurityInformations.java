package com.projetenchere.common.Models.Network.Communication;

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

    public SecurityInformations(String id,NetworkContactInformation networkContactInformation) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SecurityInformations that = (SecurityInformations) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
