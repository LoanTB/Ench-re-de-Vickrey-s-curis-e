package com.projetenchere.common.Model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Bid implements Serializable {
    final private String bidName;
    final private String bidDescription;
    final private LocalDateTime startDateTime;
    final private LocalDateTime endDateTime;

    public Bid(String name, String description, LocalDateTime endTime) {
        LocalDateTime localDateTime = LocalDateTime.now();
        startDateTime = LocalDateTime.from(localDateTime);
        bidDescription = description;
        bidName = name;
        endDateTime = endTime;
    }

    public String getBidName() {
        return bidName;
    }

    public String getBidDescription() {
        return bidDescription;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public String _toString() {
        return "Nom : " + bidName +
                "\nDate de création : " + startDateTime.toString() +
                "\nDescription : " + bidDescription + "." +
                "\nDate de fin : " + endDateTime.toString() + ".";
    }

    public boolean isOver() {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(endDateTime);
    }

}