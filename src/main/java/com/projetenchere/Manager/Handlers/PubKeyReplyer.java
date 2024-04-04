package com.projetenchere.Manager.Handlers;

import com.projetenchere.Manager.Loader.ManagerMain;
import com.projetenchere.Manager.Model.Manager;
import com.projetenchere.common.network.DataWrapper;
import com.projetenchere.common.network.Headers;
import com.projetenchere.common.network.IDataHandler;

import java.io.Serializable;
import java.security.PublicKey;

public class PubKeyReplyer implements IDataHandler {

    @Override
    public DataWrapper<PublicKey> handle(Serializable ignored) {
        ManagerMain.getViewInstance().tellBidRequest();
        return new DataWrapper<>(Manager.getInstance().getPublicKey(), Headers.OK_PUB_KEY);
    }
}