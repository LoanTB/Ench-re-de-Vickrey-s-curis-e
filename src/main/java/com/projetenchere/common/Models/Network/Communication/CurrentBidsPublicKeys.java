package com.projetenchere.common.Models.Network.Communication;

import com.projetenchere.common.Models.Bid;

import java.security.PublicKey;
import java.util.HashMap;

public class CurrentBidsPublicKeys {
    HashMap<Bid, PublicKey> bidKeys;

    public void addPublicKeyToBid(Bid bid, PublicKey key) {
        this.bidKeys.put(bid, key);
    }
}
