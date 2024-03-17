package com.projetenchere.common.Models.Encrypted;

import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.security.PublicKey;

//TODO : JavaDoc
public class SigPack_EncOffersProduct extends AbstractSignedPack implements Serializable {

    private final Set_SigPackEncOffer setOffers;

    /**
     * @param setProductOffers
     * @param setProductOffersSigned
     * @param signaturePubKey
     * @param list
     * @throws GeneralSecurityException
     */
    public SigPack_EncOffersProduct(byte[] setProductOffers, byte[] setProductOffersSigned, PublicKey signaturePubKey, Set_SigPackEncOffer list) throws GeneralSecurityException {
        super(setProductOffers, setProductOffersSigned, signaturePubKey);
        this.setOffers = list;
    }

    /**
     * @return
     */
    public Set_SigPackEncOffer getSetOffers() {
        return setOffers;
    }
}
