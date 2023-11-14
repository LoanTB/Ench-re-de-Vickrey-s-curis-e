package com.projetenchere.common.Models.Network.Communication;

import com.projetenchere.common.Models.Bid;

import java.io.Serializable;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class CurrentBids implements Serializable {
    private final PublicKey managerPublicKey;
    private final List<Bid> currentBids = new ArrayList<>();

    public PublicKey getManagerPublicKey() {
        return managerPublicKey;
    }

    public List<Bid> getCurrentBids() {
        return currentBids;
    }

    public void addCurrentBid(Bid bid) {
        this.currentBids.add(bid);
    }

    public void startAllBids(){
        for (Bid bid:currentBids){
            bid.startBid();
        }
    }

    public void startBids(int id) {
        for (Bid bid:currentBids){
            if (bid.getId() == id){
                bid.startBid();
            }
        }
    }

    public CurrentBids(PublicKey key) {
        managerPublicKey = key;
    }

    public boolean isOver(int id) {
        for (Bid bid:currentBids){
            if (bid.getId() == id){
                return bid.isOver();
            }
        }
        return false;
    }
}
