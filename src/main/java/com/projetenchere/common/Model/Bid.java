package com.projetenchere.common.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Bid {
    final private String BidName;
    final private String BidDescription;
    final private LocalDate date;
    public Bid(String name, String description)
    {
        LocalDateTime localDateTime = LocalDateTime.now();
        date=LocalDate.from(localDateTime);
        BidDescription=description;
        BidName=name;
    }

    public String getBidName() {
        return BidName;
    }

    public String getBidDescription() {
        return BidDescription;
    }

    public LocalDate getDate(){
        return date;
    }

    public String _toString() {
        return "Nom : "+ BidName + " Date : " + date.toString() +" Description : " + BidDescription + ".";
    }

    public boolean isOver() {
        return true;
    }

}