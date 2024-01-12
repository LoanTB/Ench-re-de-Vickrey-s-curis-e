package com.projetenchere.Seller.Model;

import com.projetenchere.common.Models.Bid;
import com.projetenchere.common.Models.Encrypted.EncryptedOffer;
import com.projetenchere.common.Models.Encrypted.EncryptedOffersSet;
import com.projetenchere.common.Models.Encrypted.SignedEncryptedOfferSet;
import com.projetenchere.common.Models.User;
import com.projetenchere.common.Models.WinStatus;
import com.projetenchere.common.Utils.SignatureUtil;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Seller extends User {
    private static Seller INSTANCE;
    private final Map<PublicKey, byte[]> bidders = new HashMap<>();
    private final Set<PublicKey> biddersOk = new HashSet<>();
    private Map<PublicKey, WinStatus> winStatusMap;
    private EncryptedOffersSet encryptedOffersReceived;
    private SignedEncryptedOfferSet encryptedOffersSignedBySeller;
    private Bid myBid;
    private boolean resultsAreIn = false;
    private boolean resultsReady = false;


    private Seller() {
    }

    public synchronized static Seller getInstance() {
        if (INSTANCE == null) INSTANCE = new Seller();
        return INSTANCE;
    }

    public synchronized boolean isResultsReady() {
        return resultsReady;
    }

    public synchronized void resultsAreReady() {
        this.resultsReady = true;
    }

    public synchronized boolean resultsAreIn() {
        return resultsAreIn;
    }

    public synchronized void setResultsAreIn(boolean resultsAreIn) {
        this.resultsAreIn = resultsAreIn;
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

    public void setWinStatusMap(Map<PublicKey, WinStatus> winStatusMap) {
        this.winStatusMap = winStatusMap;
    }

    public synchronized void finish() {
        this.resultsAreIn = true;
    }


    public Bid getMyBid() {
        return myBid;
    }

    public void setMyBid(Bid bid) {
        this.myBid = bid;
    }

    public EncryptedOffersSet getEncryptedOffersSet() {
        return this.encryptedOffersReceived;
    }

    public void setEncryptedOffers(EncryptedOffersSet offers) {
        this.encryptedOffersReceived = offers;
    }

    public synchronized SignedEncryptedOfferSet getEncryptedOffersSignedBySeller() {
        return this.encryptedOffersSignedBySeller;
    }

    public void setEncryptedOffersSignedBySeller(SignedEncryptedOfferSet encryptedOffersSignedBySeller) {
        this.encryptedOffersSignedBySeller = encryptedOffersSignedBySeller;
    }

    public synchronized void verifyAndAddOffer(EncryptedOffer offer) throws Exception {
        if (SignatureUtil.verifyDataSignature(offer.getPrice(), offer.getPriceSigned(), offer.getSignaturePublicKey())) {
            addBidder(offer.getSignaturePublicKey(), offer.getPrice());
            getEncryptedOffersSet().getOffers().add(offer);
            reSignedEncryptedOffers();
        }
    }

    public synchronized void reSignedEncryptedOffers() throws Exception {
        EncryptedOffersSet set = this.getEncryptedOffersSet();
        Set<EncryptedOffer> offers = set.getOffers();
        Set<EncryptedOffer> offersSigned = new HashSet<>();
        for (EncryptedOffer o : offers) {
            offersSigned.add(new EncryptedOffer(this.getSignature(), o.getPrice(), this.getKey(), o.getBidId()));
        }

        EncryptedOffersSet list = new EncryptedOffersSet(set.getBidId(), offersSigned);

        SignedEncryptedOfferSet offersSignedbl = new SignedEncryptedOfferSet(this.getSignature(), this.getKey(), list);

        this.setEncryptedOffersSignedBySeller(offersSignedbl);
    }

    public synchronized Set<PublicKey> getbiddersOk() {
        return this.biddersOk;
    }

}
