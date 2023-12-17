package com.projetenchere.common.Models;

import java.io.Serializable;

public class WinStatus implements Serializable {

    private final String bidId;
    private final boolean winner;
    private final double price;

    public WinStatus(String bidId, boolean status, double price){
        this.bidId = bidId;
        this.winner = status;
        this.price = price;
    }

    public String getBidId() {
        return bidId;
    }

    public boolean isWinner() {
        return winner;
    }

    public double getPrice() {
        return price;
    }
}
