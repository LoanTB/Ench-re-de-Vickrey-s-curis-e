package com.projetenchere.Seller.Controller;

import com.projetenchere.Seller.View.ISellerUserInterface;
import com.projetenchere.Seller.View.commandLineInterface.SellerCommandLineInterface;
import com.projetenchere.common.Model.Encrypted.EncryptedOffer;
import com.projetenchere.common.Model.Encrypted.EncryptedPrices;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class SellerController {

    public static final ISellerUserInterface ui = new SellerCommandLineInterface();

    public EncryptedPrices getEncryptedPrices(List<EncryptedOffer> encryptedOffers){
        Set<byte[]> encryptedPrices = new HashSet<>();
        for (EncryptedOffer encryptedOffer: encryptedOffers){
            encryptedPrices.add(encryptedOffer.getPrice());
        }
        return new EncryptedPrices(encryptedPrices);
    }

    public void sendEncryptedPrices(){}

    public void getEncryptedOffer(){}

    public static int getBidderWinner(Winner winner){}

}