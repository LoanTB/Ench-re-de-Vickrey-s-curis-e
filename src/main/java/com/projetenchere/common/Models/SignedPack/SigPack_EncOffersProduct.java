package com.projetenchere.common.Models.SignedPack;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.PublicKey;

//TODO S2 : JavaDoc
public class SigPack_EncOffersProduct extends AbstractSignedPack implements Serializable {

    private final Set_SigPackEncOffer setOffers;

    /**
     * @param setProductOffers
     * @param setProductOffersSigned
     * @param signaturePubKey
     * @param list
     * @throws GeneralSecurityException
     */
    public SigPack_EncOffersProduct(BigInteger setProductOffers, byte[] setProductOffersSigned, PublicKey signaturePubKey, Set_SigPackEncOffer list) throws GeneralSecurityException {
        super(setProductOffers, setProductOffersSigned, signaturePubKey);
        this.setOffers = list;
    }

    /**
     * @return
     */
    public synchronized Set_SigPackEncOffer getSetOffers() {
        return setOffers;
    }
}
