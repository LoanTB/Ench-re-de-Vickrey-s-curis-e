package com.projetenchere.manager.model;

import com.projetenchere.common.model.Bid;
import com.projetenchere.common.model.CurrentBids;
import com.projetenchere.common.model.User;
import com.projetenchere.common.model.signedPack.Set_SigPackEncOffer;
import com.projetenchere.common.model.signedPack.SigPack_PriceWin;
import com.projetenchere.common.model.signedPack.SigPack_PubKey;
import com.projetenchere.common.util.EncryptionUtil;
import com.projetenchere.common.util.SignatureUtil;

import java.security.PrivateKey;
import java.security.PublicKey;

public class Manager extends User {

    public static Manager INSTANCE;
    private final CurrentBids bids = new CurrentBids();
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private SigPack_PubKey publicKeySigned;

    public synchronized static Manager getInstance() {
        if (INSTANCE == null) INSTANCE = new Manager();
        return INSTANCE;
    }

    public SigPack_PubKey getPublicKeySigned() {
        return publicKeySigned;
    }

    public void setPublicKeySigned(SigPack_PubKey publicKeySigned) {
        this.publicKeySigned = publicKeySigned;
    }

    public synchronized void addBid(Bid bid) {
        bids.addCurrentBid(bid);
    }

    public synchronized PrivateKey getPrivateKey() {
        return privateKey;
    }

    public synchronized void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public synchronized PublicKey getPublicKey() {
        return publicKey;
    }

    public synchronized void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public synchronized CurrentBids getBids() {
        return bids;
    }

    public synchronized CurrentBids getBidsUnfinished() {
        return bids.currentBidsUnfinished();
    }

    public synchronized SigPack_PriceWin processPrices(Set_SigPackEncOffer setSigPackEncOffer, PrivateKey privateKey) throws Exception {
        double price1 = 0;
        byte[] encrypted1 = null;
        double decrypted;
        for (byte[] encrypted : setSigPackEncOffer.getPrices()) {
            decrypted = EncryptionUtil.decryptPrice(encrypted, privateKey);
            if (decrypted > price1) {
                price1 = decrypted;
                encrypted1 = encrypted;
            }
        }
        double price2 = -1;
        for (byte[] encrypted : setSigPackEncOffer.getPrices()) {
            decrypted = EncryptionUtil.decryptPrice(encrypted, privateKey);
            if (decrypted > price2 && decrypted != price1) {
                price2 = decrypted;
            }
        }
        if (price2 == -1) {
            price2 = price1;
        }

        byte[] price2Signed = SignatureUtil.signData(SignatureUtil.objectToArrayByte(price1),this.getSignature());
        SigPack_PriceWin priceWin = new SigPack_PriceWin(price2, price2Signed, this.getKey(), encrypted1,setSigPackEncOffer.getBidId());
        return priceWin;
    }

    //TODO S2 : Faire une nouvelle méthode pour utiliser C .
    /*
    public synchronized SigPack_PriceWin processPricesWithC(Set_SigPackEncOffer setSigPackEncOffer, PrivateKey privateKey) throws Exception{
        setSigPackEncOffer.getNbParticipant();

    }

     */
}
