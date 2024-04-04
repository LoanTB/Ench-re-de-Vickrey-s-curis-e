package com.projetenchere.common.model.signedPack;

import java.io.Serializable;
import java.security.PublicKey;

public class SigPack_Results extends AbstractSignedPack implements Serializable {

    private String bidId;

    /**
     * Constructeur de SigPack_Results qui contient le pack SigPack_PriceWin envoyé par le manager (Contenant le prix et le signé et la signature du manager), ainsi que
     * le signé et la signature du vendeur
     *
     * @param object          SigPack_PriceWin reçu par seller de manager.
     * @param priceSigned     le prix contenu dans object signé
     * @param signaturePubKey Signature du seller.
     */
    public SigPack_Results(SigPack_PriceWin object, byte[] priceSigned, PublicKey signaturePubKey, String bidId) {
        super(object, priceSigned, signaturePubKey);
        this.bidId = bidId;
    }
    public String getBidId() {
        return bidId;
    }

}
