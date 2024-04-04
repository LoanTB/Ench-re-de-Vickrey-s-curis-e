package com.projetenchere.Seller.network.Handlers;

import com.projetenchere.Seller.Loader.SellerMain;
import com.projetenchere.Seller.Model.Seller;
import com.projetenchere.Seller.View.graphicalUserInterface.SellerGraphicalUserInterface;
import com.projetenchere.common.Models.SignedPack.SigPack_EncOffer;
import com.projetenchere.common.Models.SignedPack.SigPack_EncOffersProduct;
import com.projetenchere.common.network.DataWrapper;
import com.projetenchere.common.network.Headers;
import com.projetenchere.common.network.IDataHandler;

import java.io.Serializable;

public class EncryptedOfferReplyer implements IDataHandler {
    @Override
    public DataWrapper<SigPack_EncOffersProduct> handle(Serializable data) {
        Seller seller = Seller.getInstance();
        synchronized (this) {
            if (!seller.resultsAreIn()) {
                try {
                    SigPack_EncOffer offer = (SigPack_EncOffer) data;

                    seller.verifyAndAddOffer(offer);

                    SellerMain.getViewInstance().showNewOfferAlert();
                    seller.signedProductEncryptedOffers(); //Dans cette méthode on fait le produit des chiffrés,
                                                // on signe ce produit et on créer le EncryptedOffersProductSigned.
                                                // Il contient l'ensemble des chiffrés de l'enchère.


                    //wait(3000);
                    while (seller.getBidderParticipant().size() != seller.getEncryptedOffersSet().getOffers().size()) {
                        wait(2000); //TODO : Trouver une solution pour attendre proprement
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