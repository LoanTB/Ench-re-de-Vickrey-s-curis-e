package com.projetenchere.Manager.Handlers;

import com.projetenchere.common.network.DataWrapper;
import com.projetenchere.common.network.Headers;
import com.projetenchere.common.network.IDataHandler;

import java.io.Serializable;

public class NewBidReplyer implements IDataHandler {
    @Override
    public <T extends Serializable> DataWrapper<T> handle(Serializable data) {
        //TODO: À implémenter
        return new DataWrapper<>(Headers.OK_NEW_BID);
    }
}
