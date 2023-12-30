package com.projetenchere.Manager.Handlers;

import com.projetenchere.Manager.Model.Manager;
import com.projetenchere.common.Models.Encrypted.EncryptedOffer;
import com.projetenchere.common.Models.Encrypted.EncryptedOffersSet;
import com.projetenchere.common.Models.Winner;
import com.projetenchere.common.Utils.SignatureUtil;
import com.projetenchere.common.network.DataWrapper;
import com.projetenchere.common.network.Headers;
import com.projetenchere.common.network.IDataHandler;

import java.io.Serializable;
import java.security.PublicKey;
import java.util.HashSet;
import java.util.Set;

//Manager reçoit les prix de chiffrés du vendeur.

public class EncryptedOffersSetReplyer implements IDataHandler {
    @Override
    public DataWrapper<Winner> handle(Serializable data) {
        Manager manager = Manager.getInstance();
        try {
            EncryptedOffersSet enc = (EncryptedOffersSet) data;
            Set<EncryptedOffer> offers = enc.getOffers();
            Set<EncryptedOffer> offersToRemove = new HashSet<>();

            PublicKey sellerPubKey = manager.getBids().getBid(enc.getBidId()).getSellerSignaturePublicKey();
            for (EncryptedOffer o : offers){
                boolean verify = SignatureUtil.verifyDataSignature(o.getPrice(),o.getPriceSigned(),sellerPubKey);
                if(!verify){
                    offersToRemove.add(o);
                }
            }
            if(!offersToRemove.isEmpty()){
                for(EncryptedOffer o : offersToRemove){
                    offers.remove(o);
                }
            }

            return new DataWrapper<>(manager.processPrices(new EncryptedOffersSet(enc.getBidId(),offers), manager.getPrivateKey()), Headers.RESOLVE_BID_OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
            //return new DataWrapper<>(null, Headers.ERROR);
        }
    }
}
