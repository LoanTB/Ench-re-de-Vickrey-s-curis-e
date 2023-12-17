package com.projetenchere.Seller.Model;

import com.projetenchere.common.Models.Encrypted.EncryptedOffer;
import com.projetenchere.common.Models.WinStatus;
import com.projetenchere.common.Models.Identity;
import com.projetenchere.common.Models.User;

import java.security.Signature;
import java.util.*;

public class Seller {
    private static Seller INSTANCE;
    private final Map<Signature, byte[]> bidders = new HashMap<>();
    private final Map<Signature, WinStatus> winStatusMap = new HashMap<>();
    private final Set<EncryptedOffer> encryptedOffers = new HashSet<>();
    private boolean resultsAreIn = false;

    public synchronized boolean resultsAreIn() {
        return resultsAreIn;
    }

    public synchronized void setResultsAreIn(boolean resultsAreIn) {
        this.resultsAreIn = resultsAreIn;
    }

    public synchronized static Seller getInstance() {
        if (INSTANCE == null) INSTANCE = new Seller();
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

    private Seller(){}

    public Set<EncryptedOffer> getEncryptedOffers() {
        return this.encryptedOffers;
    }


}
