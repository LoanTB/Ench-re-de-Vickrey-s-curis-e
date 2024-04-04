package com.projetenchere.manager.network.handler;

import com.projetenchere.manager.loader.ManagerMain;
import com.projetenchere.manager.model.Manager;
import com.projetenchere.common.model.signedPack.SigPack_CurrentBids;
import com.projetenchere.common.util.SignatureUtil;
import com.projetenchere.common.network.DataWrapper;
import com.projetenchere.common.network.Headers;
import com.projetenchere.common.network.IDataHandler;

import java.io.Serializable;

public class BidSetReplyer implements IDataHandler {
    @Override
    public DataWrapper<SigPack_CurrentBids> handle(Serializable ignored) {
        Manager manager = Manager.getInstance();
        try {
            ManagerMain.getViewInstance().displayBidderAskBids();

            byte[] signedBids = SignatureUtil.signData(manager.getBidsUnfinished(),manager.getSignature());
            SigPack_CurrentBids bids = new SigPack_CurrentBids(manager.getBidsUnfinished(),signedBids,manager.getKey());

            return new DataWrapper<>(bids, Headers.OK_CURRENT_BIDS);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //TODO / ajout de else return new DataWrapper<>(null, Headers.ERROR);

    }
}