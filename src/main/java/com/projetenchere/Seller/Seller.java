package com.projetenchere.Seller;

import com.projetenchere.common.Model.Encrypted.EncryptedOffer;
import com.projetenchere.common.Model.Encrypted.EncryptedPrices;

import java.util.*;

class Seller {

    private ArrayList<EncryptedOffer> encryptedOffers;

    public EncryptedPrices getEncryptedPrices(){
        Set<byte[]> encryptedPrices = new HashSet<>();
        for (EncryptedOffer encryptedOffer: encryptedOffers){
            encryptedPrices.add(encryptedOffer->getPrice());
        }
        return new EncryptedPrices(encryptedPrices);
    }

    public void sendEncryptedPrices(){}

    public void getEncryptedOffer(){}

    public static int getBidderWinner(Winner winner){}

}