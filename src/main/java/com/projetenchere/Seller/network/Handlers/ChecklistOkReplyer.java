package com.projetenchere.Seller.network.Handlers;

import com.projetenchere.Seller.Model.Seller;
import com.projetenchere.Seller.View.graphicalUserInterface.SellerGraphicalUserInterface;
import com.projetenchere.common.Models.PlayerStatus;
import com.projetenchere.common.Models.EndPack;
import com.projetenchere.common.Models.SignedPack.SigPack_EncOffer;
import com.projetenchere.common.Models.SignedPack.SigPack_Confirm;
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
    public DataWrapper<EndPack> handle(Serializable data) {
        Seller seller = Seller.getInstance();
        synchronized (this) {

            try {
                SigPack_Confirm signedPublicKey = (SigPack_Confirm) data;
                PublicKey bidderPk = null;
                PlayerStatus status;

                (SellerGraphicalUserInterface.getInstance()).addLogMessage("Confirmation reçue...");



                byte[] noPresent = SignatureUtil.objectToArrayByte(0);
                byte[] present = SignatureUtil.objectToArrayByte(1);

                boolean ok = false;
                Set<PublicKey> participants = seller.getBidderParticipant();

                String msg = "";
                for (PublicKey participant : participants) {
                    if (participant == signedPublicKey.getSignaturePubKey() && signedPublicKey.getBidId().equals(seller.getMyBid().getId())) {
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

                if(ok){
                    msg = "et a été authentifié.";
                }
                else {
                    msg = "mais a été falsifié.";
                }
                (SellerGraphicalUserInterface.getInstance()).addLogMessage("Un participant a confirmé la présence de son chiffré "+msg);


                if(!ok){
                    status = new PlayerStatus(seller.getMyBid().getId());
                    status.eject();    //Si l'enchérisseur précise qu'il n'y a pas son chiffré dans la liste
                    EndPack end = new EndPack(status);
                    return new DataWrapper<>(end, Headers.OK_RESULTS);
                }
                if(bidderPk == null)
                {
                    status = new PlayerStatus(seller.getMyBid().getId());
                    status.unknown();
                        // qu'un enchérisseur répond sans qu'on ai sa clé publique parmi
                        // les clé des enchérisseur ayant participé lorsque l'enchère était en cours.
                    EndPack end = new EndPack(status);
                    return new DataWrapper<>(end, Headers.OK_RESULTS);
                }

                while (!seller.resultsAreIn()) {
                    wait(1000);
                }
                EndPack end = new EndPack(seller.getEndResults());
                return new DataWrapper<>(end, Headers.OK_RESULTS);
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