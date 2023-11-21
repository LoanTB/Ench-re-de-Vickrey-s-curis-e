package com.projetenchere.common.Models.Network.Communication;

import java.io.Serializable;
import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;

public class CurrentBidsPrivateKeys implements Serializable {
    Map<String, KeyPair> bidKeys = new HashMap<>();

    public void addKeyToBid(String idBid, KeyPair key) {
        this.bidKeys.put(idBid, key);
    }

    public KeyPair getPublicKeyOfBid(String idBid){
        return bidKeys.get(idBid);
    }
}
