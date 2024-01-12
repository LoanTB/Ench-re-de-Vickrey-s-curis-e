package com.projetenchere.Manager.Handlers;

import com.projetenchere.Manager.Model.Manager;
import com.projetenchere.Manager.View.graphicalUserInterface.ManagerGraphicalUserInterface;
import com.projetenchere.common.Models.Bid;
import com.projetenchere.common.network.DataWrapper;
import com.projetenchere.common.network.Headers;
import com.projetenchere.common.network.IDataHandler;

import java.io.Serializable;

public class NewBidReplyer implements IDataHandler {
    @Override
    public <T extends Serializable> DataWrapper<T> handle(Serializable data) {
        Manager.getInstance().addBid((Bid) data);
        ((ManagerGraphicalUserInterface) ManagerGraphicalUserInterface.getInstance()).displayNewBid((Bid) data);
        return new DataWrapper<>(Headers.OK_NEW_BID);
    }
}
