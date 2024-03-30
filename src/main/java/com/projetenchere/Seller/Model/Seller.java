package com.projetenchere.Seller.Model;

import com.projetenchere.common.Models.Bid;
import com.projetenchere.common.Models.PlayerStatus;
import com.projetenchere.common.Models.SignedPack.SigPack_EncOffer;
import com.projetenchere.common.Models.SignedPack.SigPack_EncOffersProduct;
import com.projetenchere.common.Models.SignedPack.Set_SigPackEncOffer;
import com.projetenchere.common.Models.SignedPack.SigPack_Results;
import com.projetenchere.common.Models.User;
import com.projetenchere.common.Utils.SignatureUtil;

import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Seller extends User {
    private static Seller INSTANCE;

    private final Map<PublicKey, byte[]> bidders = new HashMap<>();
    private final Set<PublicKey> biddersOk = new HashSet<>();
    private final Set<PublicKey> biddersNoOk = new HashSet<>();

    private Map<PublicKey, PlayerStatus> winStatusMap;

    private SigPack_Results EndResults = null;
    private Set_SigPackEncOffer encryptedOffersReceived;
    private SigPack_EncOffersProduct offersProductSignedBySeller; //Réponse aux enchérisseurs.
    private Bid myBid;

    private boolean resultsAreIn = false;
    private boolean resultsReady = false;
    private boolean winnerExpressed = false;


    private Seller() {
    }

    public synchronized static Seller getInstance() {
        if (INSTANCE == null) INSTANCE = new Seller();
        return INSTANCE;
    }



    public SigPack_Results getEndResults() {
        return EndResults;
    }

    public void setEndResults(SigPack_Results endResults) {
        EndResults = endResults;
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

    public boolean isWinnerExpressed() {
        return winnerExpressed;
    }

    public void winnerExpressed() {
        this.winnerExpressed = true;
    }



    public synchronized void addBidder(PublicKey key, byte[] price) {
        this.bidders.put(key, price);
    }

    public synchronized Map<PublicKey, byte[]> getBidders() {
        return this.bidders;
    }



    public synchronized PlayerStatus getSignatureWinStatus(PublicKey key) {
        return winStatusMap.get(key);
    }

    public Map<PublicKey, PlayerStatus> getWinStatusMap() {
        return winStatusMap;
    }

    public void setWinStatusMap(Map<PublicKey, PlayerStatus> winStatusMap) {
        this.winStatusMap = winStatusMap;
    }



    public Bid getMyBid() {
        return myBid;
    }

    public void setMyBid(Bid bid) {
        this.myBid = bid;
    }

    public Set_SigPackEncOffer getEncryptedOffersSet() {
        return this.encryptedOffersReceived;
    }

    public void setEncryptedOffers(Set_SigPackEncOffer offers) {
        this.encryptedOffersReceived = offers;
    }


    public SigPack_EncOffersProduct getOffersProductSignedBySeller() {
        return offersProductSignedBySeller;
    }

    public void setOffersProductSignedBySeller(SigPack_EncOffersProduct offersProductSignedBySeller) {
        this.offersProductSignedBySeller = offersProductSignedBySeller;
    }

    public synchronized void verifyAndAddOffer(SigPack_EncOffer offer) throws Exception {
        if (SignatureUtil.verifyDataSignature(SignatureUtil.objectToArrayByte(offer.getObject()), offer.getObjectSigned(), offer.getSignaturePubKey())) {
            addBidder(offer.getSignaturePubKey(), SignatureUtil.objectToArrayByte(offer.getObject()));
            getEncryptedOffersSet().getOffers().add(offer);
        }
    }

    public synchronized void signedProductEncryptedOffers() throws GeneralSecurityException {

        Set<SigPack_EncOffer> offers = getEncryptedOffersSet().getOffers();
        BigInteger product = BigInteger.valueOf(1);
        for(SigPack_EncOffer o : offers)
        {
            BigInteger x = new BigInteger(SignatureUtil.objectToArrayByte(o.getObject()));
            product = product.multiply(x); //Vaut 1 tout le temps.
        }

        byte[] setProductOffersSigned = SignatureUtil.signData(product.toByteArray(), this.getSignature());
        SigPack_EncOffersProduct set = new SigPack_EncOffersProduct(product,setProductOffersSigned,this.getKey(),getEncryptedOffersSet());
        this.setOffersProductSignedBySeller(set);
    }

    public synchronized Set<PublicKey> getbiddersOk() {
        return this.biddersOk;
    }
    public synchronized Set<PublicKey> getBiddersNoOk() {
        return biddersNoOk;
    }



}
