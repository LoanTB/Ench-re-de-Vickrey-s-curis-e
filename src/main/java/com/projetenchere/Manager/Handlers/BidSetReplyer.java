package com.projetenchere.Manager.Handlers;

import com.projetenchere.Manager.Model.ManagerInfos;
import com.projetenchere.common.Models.Network.Communication.CurrentBids;
import com.projetenchere.common.network.DataWrapper;
import com.projetenchere.common.network.Handlers.data.DataHandlerWithReply;
import com.projetenchere.common.network.Handlers.data.IDataHandler;
import com.projetenchere.common.network.Headers;

import java.security.PublicKey;

public class BidSetReplyer extends DataHandlerWithReply {
    @Override
    public DataWrapper<CurrentBids> handle() {
        DataWrapper<CurrentBids> data = new DataWrapper<>(
                ManagerInfos.getInstance().getBids(),
                Headers.OK_PUB_KEY,
                null);
        return data;
    }
}

