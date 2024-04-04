package com.projetenchere.manager.network.handler;

import com.projetenchere.common.model.signedPack.SigPack_PubKey;
import com.projetenchere.common.network.DataWrapper;
import com.projetenchere.common.network.Headers;
import com.projetenchere.common.network.IDataHandler;
import com.projetenchere.manager.loader.ManagerMain;
import com.projetenchere.manager.model.Manager;

import java.io.Serializable;

public class PubKeyReplyer implements IDataHandler {

    @Override
    public DataWrapper<SigPack_PubKey> handle(Serializable ignored) {
        ManagerMain.getViewInstance().displaySendBidderPubKey();
        return new DataWrapper<>(Manager.getInstance().getPublicKeySigned(), Headers.OK_PUB_KEY);
    }
}