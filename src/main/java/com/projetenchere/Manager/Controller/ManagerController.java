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
import java.util.ArrayList;

public class ManagerController {

    public static final IManagerUserInterface ui = new ManagerCommandLineInterface();

    //Generer
    public KeyPair generateManagerKeys() throws Exception {
        return EncryptionUtil.generateKeyPair();
    }

    //Creer l'enchère.

    public Bid createBid()
    {
        return ui.askBidInformations();
    }


    //Déchiffrer les offres.
    public void decryptEncryptedOffers(Set<EncryptedPrice> ReceivedPrices){ //ADD : retour List<Offer>
        //TODO : Decrypt each offers.
        return ;
    }

    public void showPrices(List<Double> DecryptedPrice){
        ui.displayPrices(DecryptedPrice);
    }

    public Winner getWinnerPrice(PublicKey managerKey, Set<EncryptedPrice> ReceivedPrices) throws Exception {

        double firstPrice = 0;
        double secondePrice = 0;
        double priceProcess = 0;
        for(EncryptedPrice index : ReceivedPrices){
            priceProcess = EncryptionUtil.decrypt(index.getPrice());
            if(priceProcess > secondePrice){
                if(priceProcess > firstPrice){
                    secondePrice = firstPrice;
                    firstPrice = priceProcess;
                }
                else{
                    secondePrice = priceProcess;
                }
            }
        }

        byte[] winnerCypher = EncryptionUtil.encrypt(secondePrice,managerKey);

        return new Winner(winnerCypher,firstPrice);
    }

}