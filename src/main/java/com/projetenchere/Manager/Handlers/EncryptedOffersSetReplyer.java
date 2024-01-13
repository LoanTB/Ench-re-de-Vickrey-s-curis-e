package com.projetenchere.Manager.Handlers;

import com.projetenchere.Manager.Model.Manager;
import com.projetenchere.Manager.View.graphicalUserInterface.ManagerGraphicalUserInterface;
import com.projetenchere.common.Models.Encrypted.EncryptedOffer;
import com.projetenchere.common.Models.Encrypted.EncryptedOffersSet;
import com.projetenchere.common.Models.Encrypted.SignedEncryptedOfferSet;
import com.projetenchere.common.Models.Winner;
import com.projetenchere.common.Utils.SignatureUtil;
import com.projetenchere.common.network.DataWrapper;
import com.projetenchere.common.network.Headers;
import com.projetenchere.common.network.IDataHandler;

import java.io.Serializable;
import java.security.PublicKey;
import java.util.HashSet;
import java.util.Set;

public class EncryptedOffersSetReplyer implements IDataHandler {
    @Override
    public DataWrapper<Winner> handle(Serializable data) {
        Manager manager = Manager.getInstance();
        try {
            SignedEncryptedOfferSet enc = (SignedEncryptedOfferSet) data;
            Set<EncryptedOffer> offers = enc.getSet().getOffers();
            Set<EncryptedOffer> offersToRemove = new HashSet<>();

            PublicKey sellerPubKey = manager.getBids().getBid(enc.getSet().getBidId()).getSellerSignaturePublicKey();
            for (EncryptedOffer o : offers) {
                boolean verify = SignatureUtil.verifyDataSignature(o.getPrice(), o.getPriceSigned(), sellerPubKey);
                if (!verify) {
                    offersToRemove.add(o);
                }
            }
            if (!offersToRemove.isEmpty()) {
                for (EncryptedOffer o : offersToRemove) {
                    offers.remove(o);
                }
            }

            EncryptedOffersSet results = new EncryptedOffersSet(enc.getSet().getBidId(), offers);
            Winner win = manager.processPrices(results, manager.getPrivateKey());
            ((ManagerGraphicalUserInterface) ManagerGraphicalUserInterface.getInstance()).diplayEndBid(enc.getSet().getBidId());
            return new DataWrapper<>(win, Headers.RESOLVE_BID_OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
