package com.projetenchere.Seller.Model;

import com.projetenchere.common.Model.Encrypted.EncryptedOffer;

import java.util.ArrayList;
import java.util.List;

public class Seller {
    private final List<String> biddersIps = new ArrayList<>();
    private final List<Integer> biddersPorts = new ArrayList<>();
    private final List<EncryptedOffer> encryptedOffers = new ArrayList<>();

    public void addBidderIp(String ip){
        biddersIps.add(ip);
    }
    public void addBidderPort(int port){
        biddersPorts.add(port);
    }
    public void addEncryptedOffer(EncryptedOffer encryptedOffer){
        encryptedOffers.add(encryptedOffer);
    }

    public List<EncryptedOffer> getEncryptedOffers() {
        return encryptedOffers;
    }
    public List<String> getBiddersIps() {
        return biddersIps;
    }
    public List<Integer> getBiddersPorts() {
        return biddersPorts;
    }
}