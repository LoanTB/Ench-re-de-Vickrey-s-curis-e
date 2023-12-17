package com.projetenchere.Manager.Handlers;

import com.projetenchere.Manager.Model.ManagerInfos;
import com.projetenchere.common.network.DataWrapper;
import com.projetenchere.common.network.IDataHandler;
import com.projetenchere.common.network.Headers;

import java.io.Serializable;
import java.security.PublicKey;

public class PubKeyReplyer implements IDataHandler {

    @Override
    public  DataWrapper<PublicKey> handle(Serializable ignored) {
        DataWrapper<PublicKey> received = new DataWrapper<>(
                ManagerInfos.getInstance().getPublicKey(),
                Headers.OK_PUB_KEY
        );
        return received;
    }
}
