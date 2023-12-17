package com.projetenchere.Manager.Handlers;

import com.projetenchere.Manager.Model.ManagerInfos;
import com.projetenchere.common.Models.CurrentBids;
import com.projetenchere.common.network.DataWrapper;
import com.projetenchere.common.network.IDataHandler;
import com.projetenchere.common.network.Headers;

import java.io.Serializable;

public class BidSetReplyer implements IDataHandler {
    @Override
    public DataWrapper<CurrentBids> handle(Serializable ignored) {
        return new DataWrapper<>(
                ManagerInfos.getInstance().getBids(),
                Headers.OK_PUB_KEY
        );
    }
}

