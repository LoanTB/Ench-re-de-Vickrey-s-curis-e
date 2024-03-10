package com.projetenchere.Seller.network.Handlers;

import com.projetenchere.Seller.Model.Seller;
import com.projetenchere.Seller.View.graphicalUserInterface.SellerGraphicalUserInterface;
import com.projetenchere.common.Models.Encrypted.EncryptedOffer;
import com.projetenchere.common.Models.Encrypted.EncryptedOffersProductSigned;
import com.projetenchere.common.Models.Encrypted.SignedEncryptedOfferSet;
import com.projetenchere.common.network.DataWrapper;
import com.projetenchere.common.network.Headers;
import com.projetenchere.common.network.IDataHandler;

import java.io.Serializable;

public class EncryptedOfferReplyer implements IDataHandler {
    @Override
    public DataWrapper<EncryptedOffersProductSigned> handle(Serializable data) {
        Seller seller = Seller.getInstance();
        synchronized (this) {
            if (!seller.resultsAreIn()) {
                try {
                    EncryptedOffer offer = (EncryptedOffer) data;
                    seller.verifyAndAddOffer(offer);

                    SellerGraphicalUserInterface.getInstance().addLogMessage("Nouvelle offre reçue !");
                    while (!seller.getMyBid().isOver()) {
                        wait(1000);
                    }

                    seller.signedProductEncryptedOffers(); //Dans cette méthode on fait le produit des chiffrés,
                                                // on signe ce produit et on créer le EncryptedOffersProductSigned.
                                                // Il contient l'ensemble des chiffrés de l'enchère.

                    while (!seller.isResultsReady()) {
                        wait(1000);
                    }
                    return new DataWrapper<>(seller.getOffersProductSignedBySeller(), Headers.CHECK_LIST);
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