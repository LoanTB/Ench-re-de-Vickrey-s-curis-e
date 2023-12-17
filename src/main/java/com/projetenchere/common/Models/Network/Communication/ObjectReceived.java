package com.projetenchere.common.Models.Network.Communication;

import com.projetenchere.common.Models.Network.Sendable.ObjectSender;

import java.io.Serializable;

public class ObjectReceived implements Serializable {

    private final ObjectSender objectSended;
    private final AuthenticationStatus authenticationStatus;

    public ObjectReceived(ObjectSender objectSended) {
        this.objectSended = objectSended;
        authenticationStatus = null;
    }

    public ObjectReceived(ObjectSender objectSended, AuthenticationStatus authenticationStatus) {
        this.objectSended = objectSended;
        this.authenticationStatus = authenticationStatus;
    }

    public ObjectSender getObjectSended() {
        return objectSended;
    }

    public AuthenticationStatus getAuthenticationStatus() {
        return authenticationStatus;
    }
}

