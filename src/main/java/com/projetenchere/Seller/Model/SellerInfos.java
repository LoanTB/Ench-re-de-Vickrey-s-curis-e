package com.projetenchere.Seller.Model;

import com.projetenchere.common.Models.WinStatus;

import java.security.Signature;
import java.util.HashMap;
import java.util.Map;

public class SellerInfos {
    private static SellerInfos INSTANCE;
    private final Map<Signature, byte[]> bidders = new HashMap<>();
    private final Map<Signature, WinStatus> winStatusMap = new HashMap<>();

    private boolean resultsAreIn = false;

    public synchronized boolean resultsAreIn() {
        return resultsAreIn;
    }

    public synchronized void setResultsAreIn(boolean resultsAreIn) {
        this.resultsAreIn = resultsAreIn;
    }

    public synchronized static SellerInfos getInstance() {
        if (INSTANCE == null) INSTANCE = new SellerInfos();
        return INSTANCE;
    }

    public synchronized void addBidder(Signature signature, byte[] price) {
        this.bidders.put(signature, price);
    }

    public synchronized WinStatus getSignatureWinStatus(Signature signature) {
        return winStatusMap.get(signature);
    }

    public synchronized Map<Signature, byte[]> getBidders() {
        return this.bidders;
    }

    public Map<Signature, WinStatus> getWinStatusMap() {
        return winStatusMap;
    }

    public synchronized void finish() {
        this.resultsAreIn = true;
    }

    private SellerInfos(){}
}
