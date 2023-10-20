package com.projetenchere.common.Model;

import java.text.DecimalFormat;

public class Offer {
    private final String idBidder;
    private final double value;

    public Offer(String idBidder, double value) {
        this.idBidder = idBidder;
        this.value = value;
    }

    public Offer(String idBidder, String value){
        this.idBidder = idBidder;
        this.value = Double.parseDouble(value);
    }

    public String getIdBidder() {
        return idBidder;
    }

    public double getValue() {
        return value;
    }
}


