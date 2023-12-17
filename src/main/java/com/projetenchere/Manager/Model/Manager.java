package com.projetenchere.Manager.Model;

import com.projetenchere.common.Models.User;

import java.net.InetSocketAddress;
import java.security.KeyPair;
import com.projetenchere.common.Models.Bid;
import com.projetenchere.common.Models.CurrentBids;
import com.projetenchere.common.Models.Encrypted.EncryptedPrices;
import com.projetenchere.common.Models.Winner;
import com.projetenchere.common.Utils.EncryptionUtil;

import java.security.PrivateKey;
import java.security.PublicKey;

public class Manager extends User{

    private PrivateKey privateKey;
    private PublicKey publicKey;
    private final CurrentBids bids = new CurrentBids();
    public static Manager INSTANCE;

    public static Manager getInstance() {
        if (INSTANCE == null) INSTANCE = new Manager();
        return INSTANCE;
    }

    private Manager(){}

    public synchronized void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }
    public synchronized void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public synchronized void addBid(Bid bid) {
        bids.addCurrentBid(bid);
    }

    public synchronized PrivateKey getPrivateKey() {
        return privateKey;
    }
    public synchronized PublicKey getPublicKey() {
        return publicKey;
    }

    public synchronized CurrentBids getBids() {
        return bids;
    }

    public Winner processPrices(EncryptedPrices encryptedPrices, PrivateKey privateKey) throws Exception {
        double price1 = 0;
        byte[] encrypted1 = null;
        double decrypted;
        for (byte[] encrypted : encryptedPrices.getPrices()) {
            decrypted = EncryptionUtil.decryptPrice(encrypted,privateKey);
            if (decrypted > price1){
                price1 = decrypted;
                encrypted1 = encrypted;
            }
        }
        double price2 = -1;
        for (byte[] encrypted : encryptedPrices.getPrices()) {
            decrypted = EncryptionUtil.decryptPrice(encrypted,privateKey);
            if (decrypted > price2 && decrypted != price1){
                price2 = decrypted;
            }
        }
        if (price2 == -1){
            price2 = price1;
        }
        Winner winner = new Winner(encryptedPrices.getBidId(), encrypted1,price2);
        return winner;
    }

}
