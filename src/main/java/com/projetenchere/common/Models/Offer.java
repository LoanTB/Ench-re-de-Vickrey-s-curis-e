package com.projetenchere.common.Models;

import java.io.Serializable;

public class Offer implements Serializable {
    private final String idBidder;
    private final String idBid;
    private final double price;

    public Offer(String idBidder, String idBid, String price){
        this.idBidder = idBidder;
        this.idBid = idBid;
        this.price = Double.parseDouble(price);
    }

    public String getIdBidder() {
        return idBidder;
    }

    public String getIdBid() {
        return idBidder;
    }

    public double getPrice() {
        return price;
    }
}


