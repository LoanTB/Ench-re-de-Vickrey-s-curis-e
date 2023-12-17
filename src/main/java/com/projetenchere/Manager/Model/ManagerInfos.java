package com.projetenchere.Manager.Model;

import com.projetenchere.Manager.Handlers.PubKeyReplyer;
import com.projetenchere.common.Models.Bid;
import com.projetenchere.common.Models.Network.Communication.CurrentBids;

import java.security.PublicKey;

public class ManagerInfos {

    private PublicKey publicKey;
    private CurrentBids bids;
    public static ManagerInfos INSTANCE;

    public static ManagerInfos getInstance() {
        if (INSTANCE == null) INSTANCE = new ManagerInfos();
        return INSTANCE;
    }

    private ManagerInfos(){}

    public synchronized void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public synchronized void addBid(Bid bid) {
        bids.addCurrentBid(bid);
    }

    public synchronized PublicKey getPublicKey() {
        return publicKey;
    }

    public synchronized CurrentBids getBids() {
        return bids;
    }





}
