package com.projetenchere.Manager.View.graphicalUserInterface;

import javafx.beans.property.SimpleStringProperty;

public class ItemManagerTable {

    private final String id;
    private final SimpleStringProperty nom;
    private final SimpleStringProperty description;
    private final SimpleStringProperty debut;
    private final SimpleStringProperty fin;
    private SimpleStringProperty status;

    public ItemManagerTable(String id, String nom, String description, String debut, String fin, String status) {
        this.id = id;
        this.nom = new SimpleStringProperty(nom);
        this.description = new SimpleStringProperty(description);
        this.debut = new SimpleStringProperty(debut);
        this.fin = new SimpleStringProperty(fin);
        this.status = new SimpleStringProperty(status);
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

    public String getDebut() {
        return debut.get();
    }

    public SimpleStringProperty debutProperty() {
        return debut;
    }

    public String getFin() {
        return fin.get();
    }

    public SimpleStringProperty finProperty() {
        return fin;
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }
}