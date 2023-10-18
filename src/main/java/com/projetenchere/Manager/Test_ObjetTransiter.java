package com.projetenchere.Manager;

import java.io.IOException;
import java.io.Serializable;

public class Test_ObjetTransiter implements Serializable {
    private int id;
    private String message;

    public Test_ObjetTransiter(int id, String message) {
        this.id = id;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
