package com.projetenchere.common.Models.Network.Communication;

import java.io.Serializable;
import java.security.PublicKey;
import java.util.HashMap;

public class CurrentBidsPublicKeys implements Serializable {
    HashMap<String,PublicKey> bidKeys;

    public void addPublicKeyToBid(String idBid, PublicKey key) {
        this.bidKeys.put(idBid, key);
    }

    public PublicKey getPublicKeyOfBid(String idBid){
        return bidKeys.get(idBid);
    }
}
