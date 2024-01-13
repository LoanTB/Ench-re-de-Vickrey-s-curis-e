package com.projetenchere.common.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CurrentBids implements Serializable {
    private final List<Bid> currentBids = new ArrayList<>();

    public List<Bid> getCurrentBids() {
        return currentBids;
    }

    public void addCurrentBid(Bid bid) {
        this.currentBids.add(bid);
    }

    public Bid getBid(String idBid) {
        for (Bid bid : currentBids) {
            if (bid.getId().equals(idBid)) {
                return bid;
            }
        }
        return null;
    }

    public void startAllBids() {
        for (Bid bid : currentBids) {
            bid.startBid();
        }
    }

    public void startBids(String id) {
        for (Bid bid : currentBids) {
            if (bid.getId().equals(id)) {
                bid.startBid();
            }
        }
    }

    @Override
    public String toString() {
        String s = "";
        for (Bid bid : currentBids) {
            s += bid.toString();
            s += "\n";
        }
        return s;
    }
}
