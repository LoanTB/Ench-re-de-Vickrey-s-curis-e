package com.projetenchere.common.Models.Network.Communication;

import java.io.Serializable;
import java.security.KeyPair;
import java.util.HashMap;

public class CurrentBidsPrivateKeys implements Serializable {
    HashMap<String, KeyPair> bidKeys;

    public void addPublicKeyToBid(String idBid, KeyPair key) {
        this.bidKeys.put(idBid, key);
    }

    public KeyPair getPublicKeyOfBid(String idBid){
        return bidKeys.get(idBid);
    }
}
