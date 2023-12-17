package com.projetenchere.Manager.Handlers;

import com.projetenchere.Manager.Model.Manager;
import com.projetenchere.common.network.DataWrapper;
import com.projetenchere.common.network.IDataHandler;
import com.projetenchere.common.network.Headers;

import java.io.Serializable;
import java.security.PublicKey;

//Enchérisseurs obtiennent la clé publique du Manager

public class PubKeyReplyer implements IDataHandler {

    @Override
    public  DataWrapper<PublicKey> handle(Serializable ignored) {
        DataWrapper<PublicKey> received = new DataWrapper<>(
                Manager.getInstance().getPublicKey(),
                Headers.OK_PUB_KEY
        );
        return received;
    }
}
