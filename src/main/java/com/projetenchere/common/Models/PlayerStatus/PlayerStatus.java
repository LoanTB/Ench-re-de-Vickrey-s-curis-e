package com.projetenchere.common.Models.PlayerStatus;

import java.io.Serializable;

public class PlayerStatus implements Serializable {
    protected final String bidId;
    private boolean ejected = false;
    private boolean winner = false;
    private boolean unknown = false;
    private final double price;

    public PlayerStatus(String bidId, double price) {
        this.bidId = bidId;
        this.price = price;
    }
    public PlayerStatus(String bidId, double price, boolean win) {
        this.bidId = bidId;
        this.price = price;
        this.winner = win;
    }
    public String getBidId() {
        return bidId;
    }

    public double getPrice() {
        return price;
    }

    //Ejected = L'enchérisseur a précisé que son chiffré n'était pas dans la liste, donc le signé n'était pas correct.
    public boolean isEjected() {
        return ejected;
    }
    public void eject(){
        ejected = true;
    }

    //Unknwon = Le seller n'a pas la clé publique de l'enchérisseur dans sa liste.
    public void unknown(){
        this.unknown = true;
    }
    public boolean isUnknown(){
        return unknown;
    }

    public boolean isWinner() {
        return winner;
    }
    public void win(){
        winner = true;
    }


}
