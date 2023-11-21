package com.projetenchere.common.Models;

public class Identity {
    private final String id;
    private final String name;
    private final String surname;
    private final String type;

    public Identity(String id, String type) {
        this.id = id;
        this.name = null;
        this.surname = null;
        this.type = type;
    }

    public Identity(String id, String name, String surname, String type) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getType() {
        return type;
    }
}
