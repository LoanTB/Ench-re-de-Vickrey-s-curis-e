package com.projetenchere.Seller.network.Handlers;

import com.projetenchere.Seller.Model.Seller;
import com.projetenchere.common.Models.Encrypted.EncryptedOffer;
import com.projetenchere.common.Models.WinStatus;
import com.projetenchere.common.Utils.SignatureUtil;
import com.projetenchere.common.network.DataWrapper;
import com.projetenchere.common.network.Headers;
import com.projetenchere.common.network.IDataHandler;

import java.io.Serializable;
import java.security.SignatureException;
import java.util.Set;

public class ChecklistOkReplyer implements IDataHandler {
    @Override
    public <T extends Serializable> DataWrapper<T> handle(Serializable data) {
        Seller seller = Seller.getInstance();
        synchronized (this) {
            if (!seller.resultsAreIn()) {
                try {
                    Set<EncryptedOffer> offers = seller.getEncryptedOffers();
                    //TODO : Comment j'identifie à ce moment là le bidder ???

                    while (!seller.resultsAreIn()) { //TODO : Mettre un autre timer ???
                        wait(1000);
                    }
                    WinStatus status = seller.getSignatureWinStatus(???.getSignaturePublicKey());
                    return new DataWrapper<>(status, Headers.OK_WIN_STATUS);
                } catch (ClassCastException e) {
                    throw new RuntimeException("Received unreadable data");
                } catch (InterruptedException e) {
                    throw new RuntimeException("Timeout");
                } catch (SignatureException e) {
                    throw new RuntimeException("Signature falsify");
                }
            } else return new DataWrapper<>(null, Headers.ERROR);
        }
    }
}
