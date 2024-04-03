package com.projetenchere.Seller.network.Handlers;

import com.projetenchere.Seller.Model.Seller;
import com.projetenchere.Seller.SellerApp;
import com.projetenchere.Seller.View.graphicalUserInterface.SellerGraphicalUserInterface;
import com.projetenchere.common.Models.Encrypted.EncryptedOffer;
import com.projetenchere.common.Models.Encrypted.SignedEncryptedOfferSet;
import com.projetenchere.common.network.DataWrapper;
import com.projetenchere.common.network.Headers;
import com.projetenchere.common.network.IDataHandler;

import java.io.Serializable;

public class EncryptedOfferReplyer implements IDataHandler {
    @Override
    public DataWrapper<SignedEncryptedOfferSet> handle(Serializable data) {
        Seller seller = Seller.getInstance();
        synchronized (this) {
            if (!seller.resultsAreIn()) {
                try {
                    EncryptedOffer offer = (EncryptedOffer) data;
                    seller.verifyAndAddOffer(offer);
                    SellerApp.getViewInstance().showNewOfferAlert();
                    while (!seller.getMyBid().isOver()) {
                        wait(1000);
                    }
                    seller.reSignedEncryptedOffers();

                    while (!seller.isResultsReady()) {
                        wait(1000);
                    }
                    return new DataWrapper<>(seller.getEncryptedOffersSignedBySeller(), Headers.CHECK_LIST);
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