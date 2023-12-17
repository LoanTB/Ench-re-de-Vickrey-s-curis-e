package com.projetenchere.Manager.Handlers;

import com.projetenchere.Manager.Model.Manager;
import com.projetenchere.Manager.Model.ManagerInfos;
import com.projetenchere.Seller.Model.SellerInfos;
import com.projetenchere.common.Models.Encrypted.EncryptedPrices;
import com.projetenchere.common.Models.WinStatus;
import com.projetenchere.common.Models.Winner;
import com.projetenchere.common.network.DataWrapper;
import com.projetenchere.common.network.Headers;
import com.projetenchere.common.network.IDataHandler;

import java.io.Serializable;

public class EncryptedPricesReplyer implements IDataHandler {
    @Override
    public DataWrapper<Winner> handle(Serializable data) {
        ManagerInfos managerInfos = ManagerInfos.getInstance();
        try {
            return new DataWrapper<>(managerInfos.processPrices((EncryptedPrices) data,managerInfos.getPrivateKey()), Headers.OK_WIN_STATUS);
        } catch (Exception e) {
            return new DataWrapper<>(null, Headers.ERROR);
        }
    }
}
