package com.projetenchere.common.Model;

import java.text.DecimalFormat;

public class Offer {
    private final float value;

    public Offer(String value) throws NumberFormatException{
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        this.value = Float.parseFloat(df.format(value));
    }
}