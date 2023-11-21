package com.projetenchere.common.Models.Network.Communication;

import com.projetenchere.common.Models.Bid;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

public class CurrentBidsPublicKeys {
    HashMap<Bid,PublicKey> bidKeys;

    public void addPublicKeyToBid(Bid bid, PublicKey key) {
        this.bidKeys.put(bid, key);
    }

    public PublicKey getPublicKeyOfBid(String idBid){
        for (Map.Entry<Bid,PublicKey> bidKey : bidKeys.entrySet()) {
            if (bidKey.getKey().getId().equals(idBid)){
                return bidKey.getValue();
            }
        }
        return null;
    }
}
