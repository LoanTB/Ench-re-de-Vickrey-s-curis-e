package com.projetenchere.common.Models;

import java.io.Serializable;

public class PlayerStatus implements Serializable {
    protected final String bidId;
    private boolean ejected = false;
    private boolean winner = false;
    private boolean unknown = false;

    public PlayerStatus(String bidId) {
        this.bidId = bidId;
    }
    public PlayerStatus(String bidId, boolean win) {
        this.bidId = bidId;
        this.winner = win;
    }
    public String getBidId() {
        return bidId;
    }

    //Ejected = L'enchérisseur a précisé que son chiffré n'était pas dans la liste, donc le signé n'était pas correct.
    public boolean isEjected() {
        return ejected;
    }
    public synchronized void eject(){
        ejected = true;
    }

    //Unknwon = Le seller n'a pas la clé publique de l'enchérisseur dans sa liste.
    public synchronized void unknown(){
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
