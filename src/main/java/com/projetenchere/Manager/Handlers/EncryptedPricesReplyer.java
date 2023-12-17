package com.projetenchere.Manager.Handlers;

import com.projetenchere.Manager.Model.Manager;
import com.projetenchere.common.Models.Encrypted.EncryptedPrices;
import com.projetenchere.common.Models.Winner;
import com.projetenchere.common.network.DataWrapper;
import com.projetenchere.common.network.Headers;
import com.projetenchere.common.network.IDataHandler;

import java.io.Serializable;

public class EncryptedPricesReplyer implements IDataHandler {
    @Override
    public DataWrapper<Winner> handle(Serializable data) {
        Manager manager = Manager.getInstance();
        try {
            return new DataWrapper<>(manager.processPrices((EncryptedPrices) data, manager.getPrivateKey()), Headers.RESOLVE_BID_OK);
        } catch (Exception e) {
            return new DataWrapper<>(null, Headers.ERROR);
        }
    }
}
