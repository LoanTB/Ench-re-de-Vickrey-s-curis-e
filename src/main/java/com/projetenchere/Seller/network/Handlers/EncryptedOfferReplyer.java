package com.projetenchere.Seller.network.Handlers;

import com.projetenchere.Seller.Model.SellerInfos;
import com.projetenchere.common.Models.Encrypted.EncryptedOffer;
import com.projetenchere.common.Models.Network.Communication.WinStatus;
import com.projetenchere.common.network.DataWrapper;
import com.projetenchere.common.network.IDataHandler;
import com.projetenchere.common.network.Headers;

import java.io.Serializable;

public class EncryptedOfferReplyer implements IDataHandler {

    @Override
    public DataWrapper<WinStatus> handle(Serializable data) {
        SellerInfos sellerInfos = SellerInfos.getInstance();
        if (!sellerInfos.resultsAreIn()) {
            try {
                DataWrapper<EncryptedOffer> wrapper = (DataWrapper<EncryptedOffer>) data;
                EncryptedOffer offer = wrapper.unwrap();
                sellerInfos.addBidder(offer.getSignature(), offer.getPrice());
                while (!sellerInfos.resultsAreIn()) {
                    wait(1000);
                }
                WinStatus status = sellerInfos.getSignatureWinStatus(offer.getSignature());
                return new DataWrapper<>(status, Headers.OK_WIN_STATUS);
            } catch (ClassCastException e) {
                throw new RuntimeException("Received unreadable data");
            } catch (InterruptedException e) {
                throw new RuntimeException("Timeout");
            }
        } else return new DataWrapper<>(null, Headers.ERROR);
    }
}
