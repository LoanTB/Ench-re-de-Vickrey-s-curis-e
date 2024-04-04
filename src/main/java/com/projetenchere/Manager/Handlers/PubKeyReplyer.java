package com.projetenchere.Manager.Handlers;

import com.projetenchere.Manager.Loader.ManagerMain;
import com.projetenchere.Manager.Model.Manager;
import com.projetenchere.Manager.View.graphicalUserInterface.ManagerGraphicalUserInterface;
import com.projetenchere.common.Models.SignedPack.SigPack_PubKey;
import com.projetenchere.common.network.DataWrapper;
import com.projetenchere.common.network.Headers;
import com.projetenchere.common.network.IDataHandler;

import java.io.Serializable;
import java.security.PublicKey;

public class PubKeyReplyer implements IDataHandler {

    @Override
    public DataWrapper<SigPack_PubKey> handle(Serializable ignored) {
        ManagerMain.getViewInstance().displaySendBidderPubKey();
        return new DataWrapper<>(Manager.getInstance().getPublicKeySigned(), Headers.OK_PUB_KEY);
    }
}