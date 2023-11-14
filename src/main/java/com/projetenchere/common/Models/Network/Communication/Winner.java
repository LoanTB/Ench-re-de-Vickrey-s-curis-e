package com.projetenchere.common.Models.Network.Communication;

import java.io.Serializable;

public class Winner implements Serializable{
    final byte[] encryptedMaxprice;
    final double priceToPay;

    public Winner(byte[] encrypted, double price){
        encryptedMaxprice = encrypted;
        priceToPay = price;
    }

    public byte[] getEncryptedMaxprice() {
        return encryptedMaxprice;
    }

    public double getPriceToPay() {
        return priceToPay;
    }

}
