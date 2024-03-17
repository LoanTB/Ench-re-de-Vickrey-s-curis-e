package com.projetenchere.Seller.network.Handlers;

import com.projetenchere.Seller.Model.Seller;
import com.projetenchere.common.Models.PlayerStatus.PlayerStatus;
import com.projetenchere.common.Models.SignedPack.SigPack_EncOffer;
import com.projetenchere.common.Models.SignedPack.SigPack_PublicKey;
import com.projetenchere.common.Utils.SignatureUtil;
import com.projetenchere.common.network.DataWrapper;
import com.projetenchere.common.network.Headers;
import com.projetenchere.common.network.IDataHandler;

import java.io.Serializable;
import java.security.PublicKey;
import java.security.SignatureException;
import java.util.Set;

public class ChecklistOkReplyer implements IDataHandler {
    @Override
    public DataWrapper<PlayerStatus> handle(Serializable data) {
        Seller seller = Seller.getInstance();
        synchronized (this) {

            try {
                SigPack_PublicKey signedPublicKey = (SigPack_PublicKey) data;
                PublicKey bidderPk = null;
                PlayerStatus status;

//TODO : Garder une liste d'enchèrisseurs non Ok.

                byte[] noPresent = SignatureUtil.objectToArrayByte(0);
                byte[] present = SignatureUtil.objectToArrayByte(1);

                boolean ok = false;
                Set<SigPack_EncOffer> offers = seller.getEncryptedOffersSet().getOffers();
                for (SigPack_EncOffer offer : offers) {
                    if (offer.getSignaturePubKey() == signedPublicKey.getSignaturePubKey()) {
                        bidderPk = signedPublicKey.getSignaturePubKey();
                        if (SignatureUtil.verifyDataSignature(present, signedPublicKey.getObjectSigned(), signedPublicKey.getSignaturePubKey())) {
                            ok = true;
                            seller.getbiddersOk().add(bidderPk);
                        }
                        if (SignatureUtil.verifyDataSignature(noPresent, signedPublicKey.getObjectSigned(), signedPublicKey.getSignaturePubKey())) {
                            ok = false;
                            seller.getBiddersNoOk().add(bidderPk);
                        }
                    }
                }




                if(!ok){
                    status = new PlayerStatus(seller.getMyBid().getId(), 0);
                    status.eject();    //Si l'enchérisseur précise qu'il n'y a pas son chiffré dans la liste
                    return new DataWrapper<>(status, Headers.OK_PLAYER_STATUS);
                }
                if(bidderPk == null)
                {
                    status = new PlayerStatus(seller.getMyBid().getId(), 0);
                    status.unknown();
                        // qu'un enchérisseur répond sans qu'on ai sa clé publique parmi
                        // les clé des enchérisseur ayant participé lorsque l'enchère était en cours.
                    return new DataWrapper<>(status, Headers.OK_PLAYER_STATUS);
                }


                while (!seller.resultsAreIn()) {
                    wait(1000);
                }
                status = seller.getSignatureWinStatus(bidderPk);
                return new DataWrapper<>(status, Headers.OK_PLAYER_STATUS);
            } catch (ClassCastException e) {
                throw new RuntimeException("Received unreadable data");
            } catch (InterruptedException e) {
                throw new RuntimeException("Timeout");
            } catch (SignatureException e) {
                throw new RuntimeException("Signature falsify");
            }
        }
    }
}