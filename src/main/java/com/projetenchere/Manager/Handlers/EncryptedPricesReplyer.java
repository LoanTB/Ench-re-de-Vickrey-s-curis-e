package com.projetenchere.Manager.Handlers;

import com.projetenchere.common.network.DataWrapper;
import com.projetenchere.common.network.IDataHandler;

import java.io.Serializable;

public class EncryptedPricesReplyer implements IDataHandler {
    @Override
    public <T extends Serializable> DataWrapper<T> handle(Serializable data) {
        //TODO: À implémenter
        // Il faut résoudre l'enchère ICI
        return null;
    }
}
