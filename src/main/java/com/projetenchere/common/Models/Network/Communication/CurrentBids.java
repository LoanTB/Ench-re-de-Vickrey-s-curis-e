package com.projetenchere.common.Models.Network.Communication;

import com.projetenchere.common.Models.Bid;
import com.projetenchere.common.Models.Network.Communication.Informations.PublicSecurityInformations;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CurrentBids implements Serializable {
    private final PublicSecurityInformations managerInformations;
    private final List<Bid> currentBids = new ArrayList<>();

    public CurrentBids(PublicSecurityInformations managerInformations) {
        this.managerInformations = managerInformations;
    }

    public PublicSecurityInformations getManagerInformations() {
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
