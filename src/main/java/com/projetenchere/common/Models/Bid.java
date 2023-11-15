package com.projetenchere.common.Models;

import com.projetenchere.common.Models.Network.Communication.SecurityInformations;
import com.projetenchere.common.Models.Network.NetworkContactInformation;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Bid implements Serializable {

    private final int id;
    private final String name;
    private final String description;
    private LocalDateTime startDateTime = null;
    private final LocalDateTime endDateTime;
    private final SecurityInformations sellerInformations;

    public Bid(int id, String name, String description, LocalDateTime endDateTime, SecurityInformations sellerInformations) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.endDateTime = endDateTime;
        this.sellerInformations = sellerInformations;
    }

    public int getId() {
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

    public String getSellerIp(){return sellerInformations.getNetworkContactInformation().getIp();}

    public int getSellerPort(){return sellerInformations.getNetworkContactInformation().getPort();}

    public void startBid(){
        startDateTime = LocalDateTime.from(LocalDateTime.now());
    }

    public String _toString() {
        return "Nom : " + name +
                "\nDate de création : " + startDateTime.toString() +
                "\nDescription : " + description + "." +
                "\nDate de fin : " + endDateTime.toString() + ".";
    }

    public boolean isOver() {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(endDateTime);
    }
}