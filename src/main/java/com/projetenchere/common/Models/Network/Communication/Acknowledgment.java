package com.projetenchere.common.Models.Network.Communication;

public class Acknowledgment {
    private final String status;
    public Acknowledgment(String status){
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
}
