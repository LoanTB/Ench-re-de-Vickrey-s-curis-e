package com.projetenchere.Manager.Handlers;

import com.projetenchere.Manager.Model.Manager;
import com.projetenchere.Manager.View.graphicalUserInterface.ManagerGraphicalUserInterface;
import com.projetenchere.common.Models.Bid;
import com.projetenchere.common.Models.SignedPack.SigPack_Bid;
import com.projetenchere.common.Utils.SignatureUtil;
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
                ((ManagerGraphicalUserInterface) ManagerGraphicalUserInterface.getInstance()).tellFalsifiedSignatureSeller();
                throw new SignatureException("Seller's signature falsified.");
            }
            Bid bid = (Bid) sigPackBid.getObject();

            Manager.getInstance().addBid(bid);
            ((ManagerGraphicalUserInterface) ManagerGraphicalUserInterface.getInstance()).displayNewBid(bid);
            return new DataWrapper<>(Headers.OK_NEW_BID);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
