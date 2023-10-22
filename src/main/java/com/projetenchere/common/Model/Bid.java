package com.projetenchere.common.Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Bid implements Serializable {
    final private String bidName;
    final private String bidDescription;
    final private LocalDate date; // date de création
    final private LocalDateTime endDateTime; // date de fin

    public Bid(String name, String description, LocalDateTime endTime) {
        LocalDateTime localDateTime = LocalDateTime.now();
        date = LocalDate.from(localDateTime);
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

    public LocalDate getDate() {
        return date;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public String _toString() {
        return "Nom : " + bidName + "\nDate de création : " + date.toString() + "\nDescription : " + bidDescription + "." + "\nDate de fin : " + endDateTime.toString() + ".";
    }

    public boolean isOver() {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(endDateTime); // Check si le temps actuel est après la date de fin
    }

}