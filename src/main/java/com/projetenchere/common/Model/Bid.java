package com.projetenchere.common.Model;

import java.text.DecimalFormat;

public class Bid {
    private final float value;

    public Bid(String value) throws NumberFormatException{
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        this.value = Float.parseFloat(df.format(value));
    }
}


