package com.projetenchere.common.Models;

import com.projetenchere.common.Models.Network.Communication.Informations.PublicSecurityInformations;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Bid implements Serializable {

    private final String id;
    private final String name;
    private final String description;
    private LocalDateTime startDateTime = null;
    private final LocalDateTime endDateTime;
    private final PublicSecurityInformations sellerInformations;

    public Bid(String id, String name, String description, LocalDateTime endDateTime, PublicSecurityInformations sellerInformations) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.endDateTime = endDateTime;
        this.sellerInformations = sellerInformations;
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

    public PublicSecurityInformations getSeller(){return sellerInformations;}

    public String getSellerIp(){return sellerInformations.getNetworkContactInformation().ip();}

    public int getSellerPort(){return sellerInformations.getNetworkContactInformation().port();}

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