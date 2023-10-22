package com.projetenchere.common.Model;

import java.io.Serializable;
import java.security.PublicKey;

public class BidStarter implements Serializable {
    private PublicKey managerPublicKey;
    private Bid currentBid;
    private String sellerAddress;

    public String getSellerAddress() {
        return sellerAddress;
    }

    public void setSellerAddress(String addrSeller) {
        this.sellerAddress = addrSeller;
    }

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

    public BidStarter(PublicKey key, Bid bid, String addrSeller) {
        setCurrentBid(bid);
        setManagerPublicKey(key);
        setSellerAddress(addrSeller);
    }
}
