package com.projetenchere.common.Models;

import com.projetenchere.common.Models.SignedPack.SigPack_Results;

import java.io.Serializable;

public class EndPack implements Serializable {

    private PlayerStatus status = null;
    private SigPack_Results results = null;
    private final boolean resultsInside;

    public EndPack(PlayerStatus p){
        this.status = p;
        this.resultsInside = false;
    }

    public EndPack(SigPack_Results r){
        this.results = r;
        this.resultsInside = true;
    }

    public PlayerStatus getStatus() {
        return status;
    }

    public SigPack_Results getResults() {
        return results;
    }

    public boolean isResultsInside() {
        return resultsInside;
    }
}
