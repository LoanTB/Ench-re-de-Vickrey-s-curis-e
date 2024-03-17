
package com.projetenchere.Seller.network.Handlers;

import com.projetenchere.Seller.Model.Seller;
import com.projetenchere.common.Models.PlayerStatus;
import com.projetenchere.common.Models.SignedPack.SigPack_Confirm;
import com.projetenchere.common.Models.SignedPack.SigPack_EncOffer;
import com.projetenchere.common.Utils.SignatureUtil;
import com.projetenchere.common.network.DataWrapper;
import com.projetenchere.common.network.Headers;
import com.projetenchere.common.network.IDataHandler;

import java.io.Serializable;
import java.security.SignatureException;
import java.util.Set;

public class WinnerReplyer implements IDataHandler {
    @Override
    public DataWrapper<PlayerStatus> handle(Serializable data) {
        Seller seller = Seller.getInstance();
        synchronized (this) {

            try {

                SigPack_Confirm winExpression = (SigPack_Confirm) data;

                PlayerStatus status = new PlayerStatus(winExpression.getBidId());

                if(SignatureUtil.verifyDataSignature(SignatureUtil.objectToArrayByte(winExpression.getObject()),winExpression.getObjectSigned(), winExpression.getSignaturePubKey()))
                {
                    Set<SigPack_EncOffer> offers = seller.getEncryptedOffersSet().getOffers();
                    for (SigPack_EncOffer offer : offers) {
                        if (offer.getSignaturePubKey() == winExpression.getSignaturePubKey() && winExpression.getBidId().equals(seller.getMyBid().getId())) {
                            //TODO : Vérifier si c'est bien lui le gagnant
                        }
                    }
                }

                while (!seller.isWinnerExpressed()) {
                    wait(1000);
                }

                return new DataWrapper<>(status, Headers.OK_WIN_EXP);
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
