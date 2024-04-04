package com.projetenchere.common.model;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.security.PublicKey;
import java.time.LocalDateTime;

public class Bid implements Serializable {

    private final String id;
    private final String name;
    private final String description;
    private final LocalDateTime endDateTime;
    private LocalDateTime startDateTime = null;
    private InetSocketAddress sellerInformations;
    private PublicKey pubKeySignatureSeller;
    private int nbParticipant;
    private boolean canceled;

    public Bid(String id, String name, String description, LocalDateTime endDateTime, InetSocketAddress sellerInformations, PublicKey pubKeySignatureSeller) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.endDateTime = endDateTime;
        this.sellerInformations = sellerInformations;
        this.startDateTime = LocalDateTime.now();
        this.pubKeySignatureSeller = pubKeySignatureSeller;
        this.canceled = false;
    }

    public Bid(String id, String name, String description, LocalDateTime endDateTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.endDateTime = endDateTime;
        this.sellerInformations = null;
        this.startDateTime = LocalDateTime.now();
        this.pubKeySignatureSeller = null;
    }

    public synchronized String getId() {
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

    public PublicKey getSellerSignaturePublicKey() {
        return pubKeySignatureSeller;
    }

    public void startBid() {
        startDateTime = LocalDateTime.from(LocalDateTime.now());
    }

    @Override
    public String toString() {
        return this.toString(true);
    }

    public String toString(boolean withStartDate) {
        return "Id : " + id + "\nNom : " + name + (withStartDate ? "\nDate de début : " + startDateTime.toString() : "") + "\nDescription : " + description + "." + "\nDate de fin : " + endDateTime.toString() + ".";
    }

    public synchronized boolean isOver() {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(endDateTime);
    }

    public void setSellerInformations(InetSocketAddress sellerInformations) {
        this.sellerInformations = sellerInformations;
    }

    public void setPubKeySignatureSeller(PublicKey pubKeySignatureSeller) {
        this.pubKeySignatureSeller = pubKeySignatureSeller;
    }

    public int getNbParticipant() {
        return nbParticipant;
    }

    public synchronized void setNbParticipant(int nbParticipant) {
        this.nbParticipant = nbParticipant;
    }

}