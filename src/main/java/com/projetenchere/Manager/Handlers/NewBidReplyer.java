package com.projetenchere.Manager.Handlers;

import com.projetenchere.Manager.Model.Manager;
import com.projetenchere.Seller.View.graphicalUserInterface.SellerGraphicalUserInterface;
import com.projetenchere.common.Models.Bid;
import com.projetenchere.common.network.DataWrapper;
import com.projetenchere.common.network.Headers;
import com.projetenchere.common.network.IDataHandler;

import java.io.Serializable;

//Seller demander à faire une enchère au manager

public class NewBidReplyer implements IDataHandler {
    @Override
    public <T extends Serializable> DataWrapper<T> handle(Serializable data) {
        Manager.getInstance().addBid((Bid) data);
        SellerGraphicalUserInterface.getInstance().addLogMessage("Nouvelle enchère reçue : "+((Bid) data).getName());
        return new DataWrapper<>(Headers.OK_NEW_BID);
    }
}
