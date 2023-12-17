package com.projetenchere.common.Models.Network.Communication;

import java.io.Serializable;

public class Winner implements Serializable{
    private final String bidId;
    final byte[] encryptedId;
    final double price;

    public Winner(String bidId, byte[] encryptedId, double price){
        this.bidId = bidId;
        this.encryptedId = encryptedId;
        this.price = price;
    }

    public String getBidId() {
        return bidId;
    }

    public byte[] getEncryptedId() {
        return encryptedId;
    }

    public double getPrice() {
        return price;
    }

}
