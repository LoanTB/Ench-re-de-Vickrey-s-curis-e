package com.projetenchere.seller.network.handler;

import com.projetenchere.common.model.signedPack.SigPack_Confirm;
import com.projetenchere.common.network.DataWrapper;
import com.projetenchere.common.network.Headers;
import com.projetenchere.common.network.IDataHandler;
import com.projetenchere.common.util.SignatureUtil;
import com.projetenchere.seller.loader.SellerMain;
import com.projetenchere.seller.model.Seller;

import java.io.Serializable;

public class ParticipationReplyer implements IDataHandler {
    @Override
    public DataWrapper<SigPack_Confirm> handle(Serializable data) {
        Seller seller = Seller.getInstance();
        synchronized (this) {
            if (!seller.resultsAreIn()) {
                try {
                    SigPack_Confirm participation = (SigPack_Confirm) data;
                    if(seller.verifyAndAddParticipant(participation)){
                        SellerMain.getViewInstance().tellNewParticipant();
                    }else {
                        SellerMain.getViewInstance().tellParticipationRejected();
                        int nbParticipant = 0;
                        byte[] nbSigned = SignatureUtil.signData(nbParticipant,seller.getSignature());
                        return new DataWrapper<>(new SigPack_Confirm(nbParticipant, nbSigned,seller.getKey(), seller.getMyBid().getId()), Headers.OK_PARTICIPATION);
                    }

                    while (!seller.getMyBid().isOver()) {
                        wait(1000);
                    }

                    int nbParticipant = seller.getBidderParticipant().size();
                    seller.getMyBid().setNbParticipant(nbParticipant);
                    seller.getEncryptedOffersSet().setNbParticipant(nbParticipant);
                    byte[] nbSigned = SignatureUtil.signData(nbParticipant,seller.getSignature());
                    return new DataWrapper<>(new SigPack_Confirm(nbParticipant, nbSigned,seller.getKey(), seller.getMyBid().getId()), Headers.OK_PARTICIPATION);
                } catch (ClassCastException e) {
                    throw new RuntimeException("Received unreadable data");
                } catch (InterruptedException e) {
                    throw new RuntimeException("Timeout");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else return new DataWrapper<>(null, Headers.ERROR);
        }
    }
}