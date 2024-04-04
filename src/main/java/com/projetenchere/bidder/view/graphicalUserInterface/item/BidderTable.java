package com.projetenchere.bidder.view.graphicalUserInterface.item;

import javafx.beans.property.SimpleStringProperty;

public class BidderTable {

    private final String id;
    private final SimpleStringProperty nom;
    private final SimpleStringProperty description;
    private final SimpleStringProperty fin;

    public BidderTable(String id, String nom, String description, String fin) {
        this.id = id;
        this.nom = new SimpleStringProperty(nom);
        this.description = new SimpleStringProperty(description);
        this.fin = new SimpleStringProperty(fin);
    }

    public String getId() {
        return id;
    }

    public String getNom() {
        return nom.get();
    }

    public SimpleStringProperty nomProperty() {
        return nom;
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public String getFin() {
        return fin.get();
    }

    public SimpleStringProperty finProperty() {
        return fin;
    }
}