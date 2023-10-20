package com.projetenchere.common.Model;

public class Winner {
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
