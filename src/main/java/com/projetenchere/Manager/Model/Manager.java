package com.projetenchere.Manager.Model;

import com.projetenchere.common.Model.Bid;
import com.projetenchere.common.Model.Encrypted.EncryptedPrices;
import com.projetenchere.common.Model.Winner;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class Manager {
    private PrivateKey managerPrivateKey;
    private PublicKey managerPublicKey;
    private Bid currentBid;
    private Winner winnerForCurrentBid;
    
    public EncryptedPrices getPricesReceived() {
        return pricesReceived;
    }

    public void setPricesReceived(EncryptedPrices pricesReceived) {
        this.pricesReceived = pricesReceived;
    }

    private EncryptedPrices pricesReceived;

    public Bid getCurrentBid() {
        return currentBid;
    }

    public void setCurrentBid(Bid currentBid) {
        this.currentBid = currentBid;
    }

    public void setManagerKeys(KeyPair managerKeys) {
        managerPrivateKey = managerKeys.getPrivate();
        managerPublicKey = managerKeys.getPublic();
    }

    public PrivateKey getManagerPrivateKey() {
        return managerPrivateKey;
    }

    public PublicKey getManagerPublicKey() {
        return managerPublicKey;
    }

    public Winner getWinnerForCurrentBid() {
        return winnerForCurrentBid;
    }

    public void setWinnerForCurrentBid(Winner winnerForCurrentBid) {
        this.winnerForCurrentBid = winnerForCurrentBid;
    }

}