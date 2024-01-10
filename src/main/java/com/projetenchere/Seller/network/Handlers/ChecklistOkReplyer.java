package com.projetenchere.Seller.network.Handlers;

import com.projetenchere.Seller.Model.Seller;
import com.projetenchere.common.Models.Encrypted.EncryptedOffer;
import com.projetenchere.common.Models.Encrypted.SignedPublicKey;
import com.projetenchere.common.Models.WinStatus;
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
    public <T extends Serializable> DataWrapper<T> handle(Serializable data) {
        Seller seller = Seller.getInstance();
        synchronized (this) {
            if (!seller.resultsAreIn()) {
                try {
                    SignedPublicKey signedPublicKey = (SignedPublicKey) data;
                    PublicKey bidderPk = null;
                    WinStatus status;

                    if(SignatureUtil.verifyDataSignature(signedPublicKey.getPublicKeySigned(),
                                                        signedPublicKey.getPublicKey().getEncoded(),
                                                        signedPublicKey.getPublicKey()))
                    {
                        Set<EncryptedOffer> offers = seller.getEncryptedOffers();

                        for(EncryptedOffer offer : offers)
                        {
                            if(offer.getSignaturePublicKey() == signedPublicKey.getPublicKey())
                            {
                                bidderPk = signedPublicKey.getPublicKey();
                            }
                        }
                    }

                    while (!seller.resultsAreIn()) { //TODO : Mettre un autre timer ???
                        wait(1000);
                    }

                    if(bidderPk == null){
                        status = new WinStatus(seller.getMyBid().getId(), false, 0);
                    }else {
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
            } else return new DataWrapper<>(null, Headers.ERROR);
        }
    }
}
