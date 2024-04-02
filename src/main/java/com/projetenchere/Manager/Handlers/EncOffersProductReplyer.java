package com.projetenchere.Manager.Handlers;

import com.projetenchere.Manager.Model.Manager;
import com.projetenchere.Manager.View.graphicalUserInterface.ManagerGraphicalUserInterface;
import com.projetenchere.common.Models.SignedPack.SigPack_EncOffer;
import com.projetenchere.common.Models.SignedPack.Set_SigPackEncOffer;
import com.projetenchere.common.Models.SignedPack.SigPack_EncOffersProduct;
import com.projetenchere.common.Models.SignedPack.SigPack_PriceWin;
import com.projetenchere.common.Utils.SignatureUtil;
import com.projetenchere.common.network.DataWrapper;
import com.projetenchere.common.network.Headers;
import com.projetenchere.common.network.IDataHandler;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.PublicKey;
import java.security.SignatureException;
import java.util.HashSet;
import java.util.Set;

public class EncOffersProductReplyer implements IDataHandler {
    @Override
    public DataWrapper<SigPack_PriceWin> handle(Serializable data) {
        Manager manager = Manager.getInstance();
        try {
            SigPack_EncOffersProduct enc = (SigPack_EncOffersProduct) data;

            if(!SignatureUtil.verifyDataSignature(SignatureUtil.objectToArrayByte(enc.getObject()), enc.getObjectSigned(), enc.getSignaturePubKey()))
            {
                ((ManagerGraphicalUserInterface) ManagerGraphicalUserInterface.getInstance()).tellFalsifiedSignatureSeller();
                throw new SignatureException("Seller's signature falsified.");
                //TODO : Trouver une meilleure fin d'enchères compromises pour les cas où la signature est usurpée.
            }
//TODO  : Utiliser le produit des chiffrés.
            if(enc.getSetOffers().getNbParticipant() == 0) {
                return new DataWrapper<>(null, Headers.RESOLVE_BID_OK); //TODO :Trouver une meilleure solution
            }
            Set_SigPackEncOffer results = enc.getSetOffers();
            SigPack_PriceWin win = manager.processPrices(results, manager.getPrivateKey());


            ((ManagerGraphicalUserInterface) ManagerGraphicalUserInterface.getInstance()).diplayEndBid(results.getBidId());
            return new DataWrapper<>(win, Headers.RESOLVE_BID_OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //TODO / ajout de else return new DataWrapper<>(null, Headers.ERROR);

    }
}
