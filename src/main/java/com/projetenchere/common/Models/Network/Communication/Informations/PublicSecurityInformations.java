package com.projetenchere.common.Models.Network.Communication.Informations;

import com.projetenchere.common.Models.Identity;

import java.io.Serializable;
import java.security.PublicKey;

public class PublicSecurityInformations implements Serializable {
    private final Identity identity;
    private final NetworkContactInformation networkContactInformation;
    private final PublicKey signaturePublicKey;
    private final PublicKey encryptionPublicKey;

    public PublicSecurityInformations(Identity identity, NetworkContactInformation networkContactInformation, PublicKey signaturePublicKey, PublicKey encryptionPublicKey) {
        this.identity = identity;
        this.networkContactInformation = networkContactInformation;
        this.signaturePublicKey = signaturePublicKey;
        this.encryptionPublicKey = encryptionPublicKey;
    }

    public PublicSecurityInformations(PrivateSecurityInformations privateSecurityInformations) {
        this.identity = privateSecurityInformations.identity();
        this.networkContactInformation = privateSecurityInformations.networkContactInformation();
        this.signaturePublicKey = privateSecurityInformations.signatureKeys().getPublic();
        this.encryptionPublicKey = privateSecurityInformations.encryptionKeys().getPublic();
    }

    public Identity getIdentity() {
        return identity;
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
        return identity.getId().equals(that.identity.getId());
    }

    @Override
    public int hashCode() {
        return identity.getId().hashCode();
    }
}
