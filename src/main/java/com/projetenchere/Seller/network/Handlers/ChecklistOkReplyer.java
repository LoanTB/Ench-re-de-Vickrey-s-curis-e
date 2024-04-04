package com.projetenchere.Seller.network.Handlers;

import com.projetenchere.Seller.Loader.SellerMain;
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
        System.out.println("test1");
        Seller seller = Seller.getInstance();
        synchronized (this) {
            try {
                SigPack_Confirm signedPublicKey = (SigPack_Confirm) data;
                PublicKey bidderPk = null;
                PlayerStatus status;

                //SellerMain.getViewInstance().addLogMessage("Confirmation reçue..."); //TODO : Ajouter une méthode dans SellerGraphical.
                System.out.println("test");


//TODO : FIX la désynchro
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

                //SellerMain.getViewInstance().addLogMessage("Confirmation reçue..."); //TODO : Ajouter une méthode dans SellerGraphical.
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

                System.out.println("a");
                while (!seller.isResultsReady()) {
                    wait(1000);
                }
                System.out.println("b");
                EndPack end = new EndPack(seller.getEndResults());
                return new DataWrapper<>(end, Headers.OK_RESULTS);
            } catch (ClassCastException e) {
                throw new RuntimeException("Received unreadable data");
            } catch (InterruptedException e) {
                throw new RuntimeException("Timeout");
            } catch (SignatureException e) {
                throw new RuntimeException("Signature falsify");
            }
            //TODO / ajout de if et else return new DataWrapper<>(null, Headers.ERROR);

        }
    }
}