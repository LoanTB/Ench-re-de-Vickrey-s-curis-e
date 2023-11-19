package com.projetenchere.Manager.Model;

import com.projetenchere.common.Models.Bid;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;

public class CurrentBidsPrivateKeys {

    HashMap<Bid, PrivateKey> bidKeys;

    public void addPublicKeyToBid(Bid bid, PrivateKey key) {
        this.bidKeys.put(bid, key);
    }
}
