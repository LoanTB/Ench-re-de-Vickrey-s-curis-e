package com.projetenchere.common.Models.Network.Communication;

import java.io.Serializable;

public class Acknowledgment implements Serializable {
    private final String status;
    public Acknowledgment(String status){
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
}
