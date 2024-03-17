package com.projetenchere.Manager.Handlers;

import com.projetenchere.Manager.Model.Manager;
import com.projetenchere.Manager.View.graphicalUserInterface.ManagerGraphicalUserInterface;
import com.projetenchere.common.Models.SignedPack.SigPack_EncOffer;
import com.projetenchere.common.Models.SignedPack.Set_SigPackEncOffer;
import com.projetenchere.common.Models.SignedPack.SigPack_PriceWin;
import com.projetenchere.common.Models.Winner;
import com.projetenchere.common.Utils.SignatureUtil;
import com.projetenchere.common.network.DataWrapper;
import com.projetenchere.common.network.Headers;
import com.projetenchere.common.network.IDataHandler;

import java.io.Serializable;
import java.security.PublicKey;
import java.util.HashSet;
import java.util.Set;

public class EncOffersProductReplyer implements IDataHandler {
    @Override
    public DataWrapper<SigPack_PriceWin> handle(Serializable data) {
        Manager manager = Manager.getInstance();
        try {
            Set_SigPackEncOffer enc = (Set_SigPackEncOffer) data;
            Set<SigPack_EncOffer> offers = enc.getOffers();
            Set<SigPack_EncOffer> offersToRemove = new HashSet<>();

            PublicKey sellerPubKey = manager.getBids().getBid(enc.getBidId()).getSellerSignaturePublicKey();
            for (SigPack_EncOffer o : offers) {
                boolean verify = SignatureUtil.verifyDataSignature(SignatureUtil.objectToArrayByte(o.getObject()), o.getObjectSigned(), sellerPubKey);
                if (!verify) {
                    offersToRemove.add(o);
                }
            }
            if (!offersToRemove.isEmpty()) {
                for (SigPack_EncOffer o : offersToRemove) {
                    offers.remove(o);
                }
            }

            Set_SigPackEncOffer results = new Set_SigPackEncOffer(enc.getBidId(), offers);
            SigPack_PriceWin win = manager.processPrices(results, manager.getPrivateKey());


            ((ManagerGraphicalUserInterface) ManagerGraphicalUserInterface.getInstance()).diplayEndBid(enc.getBidId());
            return new DataWrapper<>(win, Headers.RESOLVE_BID_OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
