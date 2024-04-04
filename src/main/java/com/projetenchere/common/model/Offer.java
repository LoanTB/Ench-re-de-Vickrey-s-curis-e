package com.projetenchere.common.model;

import java.io.Serializable;

public class Offer implements Serializable {
    private final String idBid;
    private final double price;

    public Offer(String idBid, String price) {
        this.idBid = idBid;
        this.price = Double.parseDouble(price);
    }


    public String getIdBid() {
        return idBid;
    }

    public double getPrice() {
        return price;
    }
}


