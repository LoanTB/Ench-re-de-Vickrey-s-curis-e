package com.projetenchere.Bidder.Model;

import com.projetenchere.common.Model.Offer;

public class Bidder {
    private String idBidder;
    private int port;


    public void setId(String id) {
        this.idBidder = id;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getId() {
        return this.idBidder;
    }

    public int getPort() {
        return this.port;
    }


}