
package com.projetenchere.Seller.network.Handlers;

import com.projetenchere.Seller.Model.Seller;
import com.projetenchere.common.Models.SignedPack.AbstractSignedPack;
import com.projetenchere.common.network.DataWrapper;
import com.projetenchere.common.network.Headers;
import com.projetenchere.common.network.IDataHandler;

import java.io.Serializable;

public class WinnerReplyer implements IDataHandler {
    @Override
    public DataWrapper<AbstractSignedPack> handle(Serializable data) {
        Seller seller = Seller.getInstance();
        synchronized (this) {

            try {
                SignedPublicKey signedPublicKey = (SignedPublicKey) data;
                PublicKey bidderPk = null;
                WinStatus status;


                if (SignatureUtil.verifyDataSignature("ok".getBytes(), signedPublicKey.getOkSigned(), signedPublicKey.getPublicKey())) {
                    Set<EncryptedOffer> offers = seller.getEncryptedOffersSet().getOffers();

                    for (EncryptedOffer offer : offers) {
                        if (offer.getSignaturePublicKey() == signedPublicKey.getPublicKey()) {
                            bidderPk = signedPublicKey.getPublicKey();
                            seller.getbiddersOk().add(bidderPk);
                        }
                    }
                }

                while (!seller.resultsAreIn()) {
                    wait(1000);
                }

                if (bidderPk == null) {
                    status = new WinStatus(seller.getMyBid().getId(), false, 0);
                } else {
                    status = seller.getSignatureWinStatus(bidderPk);
                }

                return new DataWrapper<>(status, Headers.OK_WIN_STATUS);
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
