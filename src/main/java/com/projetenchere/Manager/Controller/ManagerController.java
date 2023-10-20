package com.projetenchere.Manager.Controller;

import com.projetenchere.Manager.ManagerApp;
import com.projetenchere.Manager.View.IManagerUserInterface;
import com.projetenchere.Manager.View.commandLineInterface.ManagerCommandLineInterface;
import com.projetenchere.common.Model.BidStarter;
import com.projetenchere.common.Model.Bid;
import com.projetenchere.common.Model.Encrypted.EncryptedOffer;
import com.projetenchere.common.Model.Winner;
import com.projetenchere.common.Util.EncryptionUtil;

import java.security.KeyPair;
import java.security.PublicKey;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class ManagerController {

    public static final IManagerUserInterface ui = new ManagerCommandLineInterface();

    public KeyPair generateManagerKeys() throws Exception {
        return EncryptionUtil.generateKeyPair();
    }

    public Bid createBid()
    {
        return ui.askBidInformations();
    }

    public Set<Double> decryptEncryptedPrice(Set<EncryptedPrice> ReceivedPrices){ //ADD : retour List<Offer>
        //TODO : Decrypt each offers.
        Set<Double> decryptedPrice = new HashSet<>();
        for(EncryptedPrice encrypted : ReceivedPrices){
            decryptedPrice = add(EncryptionUtil.decrypt(encrypted.getPrice()));
        }
        return decryptedPrice;
    }

    public void showPrices(List<Double> DecryptedPrice){
        ui.displayPrices(DecryptedPrice);
    }

    public Winner getWinnerPrice(PublicKey managerKey, Set<Double> prices) throws Exception {

        double firstPrice = 0;
        double secondPrice = 0;

        for(Double priceProcess : prices){

            if(priceProcess > secondPrice){
                if(priceProcess > firstPrice){
                    secondPrice = firstPrice;
                    firstPrice = priceProcess;
                }
                else{
                    secondPrice = priceProcess;
                }
            }
        }

        byte[] winnerCypher = EncryptionUtil.encrypt(secondPrice,managerKey);

        return new Winner(winnerCypher,firstPrice);
    }

}