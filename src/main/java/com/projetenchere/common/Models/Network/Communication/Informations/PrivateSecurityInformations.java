package com.projetenchere.common.Models.Network.Communication.Informations;

import com.projetenchere.common.Models.Identity;

import java.security.KeyPair;

public record PrivateSecurityInformations(
        Identity identity,
        NetworkContactInformation networkContactInformation,
        KeyPair signatureKeys,
        KeyPair encryptionKeys
) { }
