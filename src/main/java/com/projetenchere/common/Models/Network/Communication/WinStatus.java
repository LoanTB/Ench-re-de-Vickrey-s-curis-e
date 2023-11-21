package com.projetenchere.common.Models.Network.Communication;

import java.io.Serializable;

public class WinStatus implements Serializable {

    private final String bidId;
    private final boolean winner;
    private final double price;
    private String nomVendeur;
    private String prenomVendeur;

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

    public String getNomVendeur() {
        return nomVendeur;
    }

    public String getPrenomVendeur() {
        return prenomVendeur;
    }
}
