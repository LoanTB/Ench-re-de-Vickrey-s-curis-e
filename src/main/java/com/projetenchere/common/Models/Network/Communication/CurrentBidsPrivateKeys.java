package com.projetenchere.common.Models.Network.Communication;

import java.security.KeyPair;
import java.util.HashMap;

public class CurrentBidsPrivateKeys {
    HashMap<String, KeyPair> bidKeys;

    public void addPublicKeyToBid(String idBid, KeyPair key) {
        this.bidKeys.put(idBid, key);
    }

    public KeyPair getPublicKeyOfBid(String idBid){
        return bidKeys.get(idBid);
    }
}
