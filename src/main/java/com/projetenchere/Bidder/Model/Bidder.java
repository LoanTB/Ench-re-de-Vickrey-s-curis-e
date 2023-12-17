package com.projetenchere.Bidder.Model;

import com.projetenchere.common.Models.Identity;
import com.projetenchere.common.Models.Network.Communication.Informations.PublicSecurityInformations;

public class Bidder {
    private Identity identity;
    private PublicSecurityInformations informations;

    public Identity getIdentity() {
        return identity;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
    }
    public PublicSecurityInformations getInformations() {
        return informations;
    }

    public void setInformations(PublicSecurityInformations informations) {
        this.informations = informations;
    }
}