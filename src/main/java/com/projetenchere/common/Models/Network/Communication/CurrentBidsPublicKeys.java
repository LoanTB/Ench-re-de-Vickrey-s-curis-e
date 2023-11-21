package com.projetenchere.common.Models.Network.Communication;

import java.io.Serializable;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

public class CurrentBidsPublicKeys implements Serializable {
    Map<String,PublicKey> bidKeys = new HashMap<>();

    public void addKeyToBid(String idBid, PublicKey key) {
        this.bidKeys.put(idBid, key);
    }

    public PublicKey getPublicKeyOfBid(String idBid){
        return bidKeys.get(idBid);
    }
}
