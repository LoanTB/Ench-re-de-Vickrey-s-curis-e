package com.projetenchere.manager.network.handler;

import com.projetenchere.common.model.signedPack.Set_SigPackEncOffer;
import com.projetenchere.common.model.signedPack.SigPack_EncOffersProduct;
import com.projetenchere.common.model.signedPack.SigPack_PriceWin;
import com.projetenchere.common.network.DataWrapper;
import com.projetenchere.common.network.Headers;
import com.projetenchere.common.network.IDataHandler;
import com.projetenchere.common.util.SignatureUtil;
import com.projetenchere.manager.loader.ManagerMain;
import com.projetenchere.manager.model.Manager;

import java.io.Serializable;
import java.security.SignatureException;

public class EncOffersProductReplyer implements IDataHandler {
    @Override
    public DataWrapper<SigPack_PriceWin> handle(Serializable data) {
        Manager manager = Manager.getInstance();
        try {
            SigPack_EncOffersProduct enc = (SigPack_EncOffersProduct) data;

            if(!SignatureUtil.verifyDataSignature(SignatureUtil.objectToArrayByte(enc.getObject()), enc.getObjectSigned(), enc.getSignaturePubKey()))
            {
                ManagerMain.getViewInstance().tellFalsifiedSignatureSeller();
                throw new SignatureException("seller's signature falsified.");
                //TODO : Trouver une meilleure fin d'enchères compromises pour les cas où la signature est usurpée.
            }
//TODO  : Utiliser le produit des chiffrés.
            if(enc.getSetOffers().getNbParticipant() == 0) {
                return new DataWrapper<>(null, Headers.RESOLVE_BID_OK); //TODO :Trouver une meilleure solution
            }
            Set_SigPackEncOffer results = enc.getSetOffers();
            SigPack_PriceWin win = manager.processPrices(results, manager.getPrivateKey());


            ManagerMain.getViewInstance().diplayEndBid(results.getBidId());
            return new DataWrapper<>(win, Headers.RESOLVE_BID_OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //TODO / ajout de else return new DataWrapper<>(null, Headers.ERROR);

    }
}
