package com.projetenchere.common.Models.Network.Communication.Informations;

import java.security.KeyPair;
import java.util.UUID;

public class PrivateSecurityInformations {
    private final String id;
    private final NetworkContactInformation networkContactInformation;
    private final KeyPair signatureKeys;
    private final KeyPair encryptionKeys;

    public PrivateSecurityInformations(NetworkContactInformation networkContactInformation, KeyPair signatureKeys, KeyPair encryptionKeys) {
        this.id = UUID.randomUUID().toString();
        this.networkContactInformation = networkContactInformation;
        this.signatureKeys = signatureKeys;
        this.encryptionKeys = encryptionKeys;
    }

    public String getId(){return id;}

    public NetworkContactInformation getNetworkContactInformation() {
        return networkContactInformation;
    }

    public KeyPair getSignatureKeys() {
        return signatureKeys;
    }

    public KeyPair getEncryptionKeys() {
        return encryptionKeys;
    }
}
