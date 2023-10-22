package com.projetenchere.common.Model;

import java.io.Serializable;
import java.text.DecimalFormat;

public class Offer implements Serializable {
    private final String idBidder;
    private final float value;

    public Offer(String idBidder, float value) {
        this.idBidder = idBidder;
        this.value = value;
    }

    public Offer(String idBidder, String value){// throws NumberFormatException
        this.idBidder = idBidder;
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        this.value = Float.parseFloat(df.format(value));
    }

    public String getIdBidder() {
        return idBidder;
    }

    public float getValue() {
        return value;
    }
}


