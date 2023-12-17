package com.projetenchere.Manager.Handlers;

import com.projetenchere.Manager.Model.Manager;
import com.projetenchere.common.Models.CurrentBids;
import com.projetenchere.common.network.DataWrapper;
import com.projetenchere.common.network.IDataHandler;
import com.projetenchere.common.network.Headers;

import java.io.Serializable;

//quand Bid demande les enchères en cours au manager.

public class BidSetReplyer implements IDataHandler {
    @Override
    public DataWrapper<CurrentBids> handle(Serializable ignored) {
        return new DataWrapper<>(
                Manager.getInstance().getBids(),
                Headers.OK_CURRENT_BIDS
        );
    }
}