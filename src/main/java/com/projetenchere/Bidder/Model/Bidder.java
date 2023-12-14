package com.projetenchere.Bidder.Model;

import com.projetenchere.common.Models.Identity;
import com.projetenchere.common.Models.Network.Communication.Informations.PublicSecurityInformations;
import com.projetenchere.common.network.Party;
import com.projetenchere.common.Utils.NetworkUtil;

import java.net.InetSocketAddress;

public class Bidder extends Party {
    private Identity identity;
    private PublicSecurityInformations informations;

    public Bidder() {
        super(NetworkUtil.getMyIP());
    }

    public Bidder(InetSocketAddress address) {
        super(address);
    }

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