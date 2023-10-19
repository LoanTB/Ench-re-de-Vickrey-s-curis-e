package com.projetenchere.common.Model;

import java.security.PublicKey;

public class BidStarter {
    private PublicKey managerPublicKey;
    private Bid currentBid;

    public PublicKey getManagerPublicKey() {
        return managerPublicKey;
    }

    public void setManagerPublicKey(PublicKey managerPublicKey) {
        this.managerPublicKey = managerPublicKey;
    }

    public Bid getCurrentBid() {
        return currentBid;
    }

    public void setCurrentBid(Bid currentBid) {
        this.currentBid = currentBid;
    }

    public BidStarter(PublicKey key, Bid bid)
    {
        setCurrentBid(bid);
        setManagerPublicKey(key);
    }
}
