package com.projetenchere.Manager.Handlers;

import com.projetenchere.Manager.Model.ManagerInfos;
import com.projetenchere.common.network.DataWrapper;
import com.projetenchere.common.network.Handlers.data.DataHandlerWithReply;
import com.projetenchere.common.network.Handlers.data.IDataHandler;
import com.projetenchere.common.network.Headers;

import java.security.PublicKey;

public class PubKeyReplyer extends DataHandlerWithReply {
    @Override
    public DataWrapper<PublicKey> handle() {
        DataWrapper<PublicKey> data = new DataWrapper<>(
                ManagerInfos.getInstance().getPublicKey(),
                Headers.OK_PUB_KEY,
                null);
        return data;
    }
}
