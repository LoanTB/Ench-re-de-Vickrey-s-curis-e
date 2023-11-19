package com.projetenchere.common.Models.Network.Communication.Informations;

import java.security.PublicKey;

public class PublicSecurityInformations {
    private final String id;
    private final NetworkContactInformation networkContactInformation;
    private final PublicKey signaturePublicKey;
    private final PublicKey encryptionPublicKey;

    public PublicSecurityInformations(String id, NetworkContactInformation networkContactInformation, PublicKey signaturePublicKey, PublicKey encryptionPublicKey) {
        this.id = id;
        this.networkContactInformation = networkContactInformation;
        this.signaturePublicKey = signaturePublicKey;
        this.encryptionPublicKey = encryptionPublicKey;
    }

    public PublicSecurityInformations(PrivateSecurityInformations privateSecurityInformations) {
        this.id = privateSecurityInformations.getId();
        this.networkContactInformation = privateSecurityInformations.getNetworkContactInformation();
        this.signaturePublicKey = privateSecurityInformations.getSignatureKeys().getPublic();
        this.encryptionPublicKey = privateSecurityInformations.getEncryptionKeys().getPublic();
    }

    public String getId() {
        return id;
    }

    public NetworkContactInformation getNetworkContactInformation() {
        return networkContactInformation;
    }

    public PublicKey getSignaturePublicKey() {return signaturePublicKey;}

    public PublicKey getEncryptionPublicKey() {
        return encryptionPublicKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PublicSecurityInformations that = (PublicSecurityInformations) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
