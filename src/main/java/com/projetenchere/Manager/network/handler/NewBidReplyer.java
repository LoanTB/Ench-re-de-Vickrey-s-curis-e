package com.projetenchere.manager.network.handler;

import com.projetenchere.manager.loader.ManagerMain;
import com.projetenchere.manager.model.Manager;
import com.projetenchere.common.model.Bid;
import com.projetenchere.common.model.signedPack.SigPack_Bid;
import com.projetenchere.common.util.SignatureUtil;
import com.projetenchere.common.network.DataWrapper;
import com.projetenchere.common.network.Headers;
import com.projetenchere.common.network.IDataHandler;

import java.io.Serializable;
import java.security.SignatureException;

public class NewBidReplyer implements IDataHandler {
    @Override
    public <T extends Serializable> DataWrapper<T> handle(Serializable data) {
        try {
            SigPack_Bid sigPackBid = (SigPack_Bid) data;

            if(!SignatureUtil.verifyDataSignature(SignatureUtil.objectToArrayByte(sigPackBid.getObject()),
                    sigPackBid.getObjectSigned(),sigPackBid.getSignaturePubKey()))
            {
                ManagerMain.getViewInstance().tellFalsifiedSignatureSeller();
                throw new SignatureException("seller's signature falsified.");
                //TODO : Trouver une meilleure fin d'enchères compromises pour les cas où la signature est usurpée.
            }
            Bid bid = (Bid) sigPackBid.getObject();

            Manager.getInstance().addBid(bid);
            ManagerMain.getViewInstance().displayNewBid(bid);
            return new DataWrapper<>(Headers.OK_NEW_BID);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
