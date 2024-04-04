
package com.projetenchere.seller.network.handler;

import com.projetenchere.common.model.PlayerStatus;
import com.projetenchere.common.model.signedPack.SigPack_Confirm;
import com.projetenchere.common.network.DataWrapper;
import com.projetenchere.common.network.Headers;
import com.projetenchere.common.network.IDataHandler;
import com.projetenchere.common.util.SignatureUtil;
import com.projetenchere.seller.model.Seller;

import java.io.Serializable;
import java.security.SignatureException;

public class WinnerReplyer implements IDataHandler {
    @Override
    public DataWrapper<PlayerStatus> handle(Serializable data) {
        Seller seller = Seller.getInstance();
        synchronized (this) {

            try {

                SigPack_Confirm winExpression = (SigPack_Confirm) data;

                PlayerStatus status = new PlayerStatus(winExpression.getBidId());
                if(!SignatureUtil.verifyDataSignature(SignatureUtil.objectToArrayByte((int) winExpression.getObject()),winExpression.getObjectSigned(), winExpression.getSignaturePubKey()))
                {
                    status.eject();
                    //TODO : Ajout message ui
                    return new DataWrapper<>(status, Headers.OK_WIN_EXP);
                    //TODO : Trouver une meilleure fin d'enchères compromises pour les cas où la signature est usurpée.
                }
                status = seller.getSignatureWinStatus(winExpression.getSignaturePubKey());
                if(status.isWinner()){
                    seller.winnerExpressed();
                }


                return new DataWrapper<>(status, Headers.OK_WIN_EXP);
            } catch (ClassCastException e) {
                throw new RuntimeException("Received unreadable data");
            } catch (SignatureException e) {
                throw new RuntimeException("Signature falsify");
            }
            //TODO / ajout de if et else return new DataWrapper<>(null, Headers.ERROR);
        }
    }
}
