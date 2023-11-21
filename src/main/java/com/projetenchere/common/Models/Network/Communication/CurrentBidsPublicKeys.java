package com.projetenchere.common.Models.Network.Communication;

import com.projetenchere.common.Models.Bid;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

public class CurrentBidsPublicKeys {
    HashMap<String,PublicKey> bidKeys;

    public void addPublicKeyToBid(String idBid, PublicKey key) {
        this.bidKeys.put(idBid, key);
    }

    public PublicKey getPublicKeyOfBid(String idBid){
        return bidKeys.get(idBid);
    }
}
