package com.projetenchere.common.Model;

import java.io.Serializable;

public class Offer implements Serializable {
    private final String idBidder;
    private final double price;

    public Offer(String idBidder, double price) {
        this.idBidder = idBidder;
        this.price = price;
    }

    public Offer(String idBidder, String price){
        this.idBidder = idBidder;
        this.price = Double.parseDouble(price);
    }

    public String getIdBidder() {
        return idBidder;
    }

    public double getPrice() {
        return price;
    }
}


