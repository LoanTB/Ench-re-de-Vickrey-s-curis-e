package com.projetenchere.common.Models;

import java.io.Serializable;
import java.security.Signature;

public class Offer implements Serializable {
    private final Signature signature;
    private final String idBid;
    private final double price;

    public Offer(Signature signature, String idBid, String price){
        this.signature = signature;
        this.idBid = idBid;
        this.price = Double.parseDouble(price);
    }

    public Signature getSignature() {
        return signature;
    }

    public String getIdBid() {
        return idBid;
    }

    public double getPrice() {
        return price;
    }
}


