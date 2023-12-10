package com.projetenchere.common.Models.Network.Communication;

import com.projetenchere.common.Models.Network.Sendable.DataWrapper;

import java.io.Serializable;

public class ObjectReceived implements Serializable {

    private final DataWrapper objectSended;
    private final AuthenticationStatus authenticationStatus;

    public ObjectReceived(DataWrapper objectSended) {
        this.objectSended = objectSended;
        authenticationStatus = null;
    }

    public ObjectReceived(DataWrapper objectSended, AuthenticationStatus authenticationStatus) {
        this.objectSended = objectSended;
        this.authenticationStatus = authenticationStatus;
    }

    public DataWrapper getObjectSended() {
        return objectSended;
    }

    public AuthenticationStatus getAuthenticationStatus() {
        return authenticationStatus;
    }
}

