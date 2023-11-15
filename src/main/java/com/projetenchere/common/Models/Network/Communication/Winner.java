package com.projetenchere.common.Models.Network.Communication;

import java.io.Serializable;

public class Winner implements Serializable{
    private final int bidId;
    final byte[] encryptedId;
    final double price;

    public Winner(int bidId, byte[] encryptedId, double price){
        this.bidId = bidId;
        this.encryptedId = encryptedId;
        this.price = price;
    }

    public int getBidId() {
        return bidId;
    }

    public byte[] getEncryptedId() {
        return encryptedId;
    }

    public double getPrice() {
        return price;
    }

}
