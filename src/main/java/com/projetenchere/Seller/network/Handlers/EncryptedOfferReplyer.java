package com.projetenchere.Seller.network.Handlers;

import com.projetenchere.Seller.Model.Seller;
import com.projetenchere.common.Models.Encrypted.EncryptedOffer;
import com.projetenchere.common.Models.WinStatus;
import com.projetenchere.common.Utils.SignatureUtil;
import com.projetenchere.common.network.DataWrapper;
import com.projetenchere.common.network.IDataHandler;
import com.projetenchere.common.network.Headers;

import java.io.Serializable;
import java.security.SignatureException;

public class EncryptedOfferReplyer implements IDataHandler {

    @Override
    public DataWrapper<WinStatus> handle(Serializable data) {
        Seller seller = Seller.getInstance();
        if (!seller.resultsAreIn()) {
            try {
                EncryptedOffer offer = (EncryptedOffer) data;

                SignatureUtil.verifyDataSignature(offer.getPrice(),offer.getPriceSigned(),offer.getPublicKey());
                seller.addBidder(offer.getPublicKey(), offer.getPrice());

                while (!seller.resultsAreIn()) {
                    wait(1000);
                }
                WinStatus status = seller.getSignatureWinStatus(offer.getPublicKey());
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
