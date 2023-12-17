package com.projetenchere.common.Models;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.time.LocalDateTime;

public class Bid implements Serializable {

    private final String id;
    private final String name;
    private final String description;
    private LocalDateTime startDateTime = null;
    private final LocalDateTime endDateTime;
    private final InetSocketAddress sellerInformations;

    public Bid(String id, String name, String description, LocalDateTime endDateTime, InetSocketAddress sellerInformations) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.endDateTime = endDateTime;
        this.sellerInformations = sellerInformations;
        this.startDateTime = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public InetSocketAddress getSellerSocketAddress() {
        return this.sellerInformations;
    }

    public void startBid(){
        startDateTime = LocalDateTime.from(LocalDateTime.now());
    }

    @Override
    public String toString() {
        return this.toString(true);
    }

    public String toString(boolean withStartDate) {
        return "Id : " + id +
                "\nNom : " + name +
                (withStartDate ? "\nDate de début : " + startDateTime.toString() : "") +
                "\nDescription : " + description + "." +
                "\nDate de fin : " + endDateTime.toString() + ".";
    }

    public boolean isOver() {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(endDateTime);
    }
}