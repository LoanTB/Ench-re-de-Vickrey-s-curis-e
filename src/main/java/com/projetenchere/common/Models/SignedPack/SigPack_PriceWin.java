package com.projetenchere.common.Models.SignedPack;

import java.io.Serializable;
import java.security.PublicKey;

public class SigPack_PriceWin extends AbstractSignedPack implements Serializable {

    //TODO : JavaDoc
//TODO : Ajouter ce qu'il faut pour l'identification du gagnant. Servira à attester que le gagnant ne triche pas.
    /**
     * Signed by Manager received by Seller
     *
     * @param price
     * @param priceSigned
     * @param signaturePubKey
     */
    public SigPack_PriceWin(double price, byte[] priceSigned, PublicKey signaturePubKey) {
        super(price, priceSigned, signaturePubKey);
    }

}
