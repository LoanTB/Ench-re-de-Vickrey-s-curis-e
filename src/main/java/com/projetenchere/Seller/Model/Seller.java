package com.projetenchere.Seller.Model;

import com.projetenchere.common.Models.Encrypted.EncryptedOffer;
import com.projetenchere.common.Models.Encrypted.EncryptedOffersSet;
import com.projetenchere.common.Models.Encrypted.SignedEncryptedOfferSet;
import com.projetenchere.common.Models.WinStatus;
import com.projetenchere.common.Models.User;
import com.projetenchere.common.Utils.SignatureUtil;

import java.security.PublicKey;
import java.security.SignatureException;
import java.util.*;

public class Seller extends User{
    private static Seller INSTANCE;
    private final Map<PublicKey, byte[]> bidders = new HashMap<>();
    private Map<PublicKey, WinStatus> winStatusMap;
    private EncryptedOffersSet encryptedOffersReceived;
    private SignedEncryptedOfferSet encryptedOffersSignedBySeller;
    private boolean resultsAreIn = false;

    private Seller(){}

    public synchronized boolean resultsAreIn() {
        return resultsAreIn;
    }

    public synchronized void setResultsAreIn(boolean resultsAreIn) {
        this.resultsAreIn = resultsAreIn;
    }

    public synchronized static Seller getInstance() {
        if (INSTANCE == null) INSTANCE = new Seller();
        return INSTANCE;
    }

    public void setWinStatusMap(Map<PublicKey, WinStatus> winStatusMap) {
        this.winStatusMap = winStatusMap;
    }

    public synchronized void addBidder(PublicKey key, byte[] price) {
        this.bidders.put(key, price);
    }

    public synchronized WinStatus getSignatureWinStatus(PublicKey key) {
        return winStatusMap.get(key);
    }

    public synchronized Map<PublicKey, byte[]> getBidders() {
        return this.bidders;
    }

    public Map<PublicKey, WinStatus> getWinStatusMap() {
        return winStatusMap;
    }

    public synchronized void finish() {
        this.resultsAreIn = true;
    }

    public Set<EncryptedOffer> getEncryptedOffers() {
        return this.encryptedOffersReceived.getOffers();
    }
    public EncryptedOffersSet getEncryptedOffersSet() {
        return this.encryptedOffersReceived;
    }

    public void setEncryptedOffers(EncryptedOffersSet offers){
        this.encryptedOffersReceived = offers;
    }


    public void setEncryptedOffersSignedBySeller(SignedEncryptedOfferSet encryptedOffersSignedBySeller){
        this.encryptedOffersSignedBySeller = encryptedOffersSignedBySeller;
    }
    public SignedEncryptedOfferSet getEncryptedOffersSignedBySeller(){
        return this.encryptedOffersSignedBySeller;
    }


    public void verifyAndAddOffer(EncryptedOffer offer) throws SignatureException {
        if (SignatureUtil.verifyDataSignature(offer.getPrice(), offer.getPriceSigned(), offer.getSignaturePublicKey())) {
            addBidder(offer.getSignaturePublicKey(), offer.getPrice());
            getEncryptedOffers().add(offer);
        }
    }

    public void reSignedEncryptedOffers() throws Exception {
        EncryptedOffersSet set = this.getEncryptedOffersSet();
        Set<EncryptedOffer> offers = set.getOffers();
        Set<EncryptedOffer> offersSigned = new HashSet<>();
        for (EncryptedOffer o : offers) {
            offersSigned.add(new EncryptedOffer(this.getSignature(), o.getPrice(), this.getKey(), o.getBidId()));
        }

        EncryptedOffersSet list = new EncryptedOffersSet(set.getBidId(), offersSigned);

        setEncryptedOffersSignedBySeller(new SignedEncryptedOfferSet(this.getSignature(), this.getKey(), list));
    }



}
