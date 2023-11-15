package com.projetenchere.common.Models.Network.Communication;

public class WinStatus {

    private final int bidId;
    private final boolean winner;
    private final double price;
    private String nomVendeur;
    private String prenomVendeur;

    public WinStatus(int bidId, boolean status, double price){
        this.bidId = bidId;
        this.winner = status;
        this.price = price;
    }

    public int getBidId() {
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
