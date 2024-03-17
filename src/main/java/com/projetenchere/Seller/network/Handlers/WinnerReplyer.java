
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


                while (!seller.resultsAreIn()) {
                    wait(1000);
                }



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
