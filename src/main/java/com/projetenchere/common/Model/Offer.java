package com.projetenchere.common.Model;

import java.text.DecimalFormat;

public class Offer {
    private final String ID_Bidder;
    private final float value;

    public Offer(String ID_Bidder, float value) {
        this.ID_Bidder = ID_Bidder;
        this.value = value;
    }

    public Offer(String ID_Bidder, String value) throws NumberFormatException{
        this.ID_Bidder = ID_Bidder;
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        this.value = Float.parseFloat(df.format(value));
    }
}


