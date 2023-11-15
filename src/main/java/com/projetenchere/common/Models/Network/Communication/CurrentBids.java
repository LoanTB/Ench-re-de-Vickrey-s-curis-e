package com.projetenchere.common.Models.Network.Communication;

import com.projetenchere.common.Models.Bid;

import java.io.Serializable;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class CurrentBids implements Serializable {
    private final SecurityInformations managerInformations;
    private final List<Bid> currentBids = new ArrayList<>();

    public CurrentBids(SecurityInformations managerInformations) {
        this.managerInformations = managerInformations;
    }

    public SecurityInformations getManagerInformations() {
        return managerInformations;
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

    public boolean isOver(int id) {
        for (Bid bid:currentBids){
            if (bid.getId() == id){
                return bid.isOver();
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String s = "CurrentBids{currentBids=";
        for (Bid bid : currentBids){
            s += bid.toString();
        }
        s += "}";
        return s;
    }
}
