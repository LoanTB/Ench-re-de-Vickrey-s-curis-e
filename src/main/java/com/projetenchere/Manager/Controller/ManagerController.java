package com.projetenchere.Manager.Controller;

import com.projetenchere.Manager.View.IManagerUserInterface;
import com.projetenchere.Manager.View.commandLineInterface.ManagerCommandLineInterface;
import com.projetenchere.common.Model.Bid;
import com.projetenchere.common.Model.Winner;
import com.projetenchere.common.Util.EncryptionUtil;
import com.projetenchere.common.Model.Encrypted.EncryptedPrices;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
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

    public String askSellerAdress(){
        return ui.askSellerAdress();
    }

    public Set<Double> decryptEncryptedPrice(EncryptedPrices receivedPrices, PrivateKey managerPrivateKey) throws Exception {
        Set<Double> decryptedPrice = new HashSet<>();
        for(byte[] encrypted : receivedPrices.getPrices()){
            decryptedPrice.add(EncryptionUtil.decrypt(encrypted,managerPrivateKey));
        }
        return decryptedPrice;
    }

    public void showPrices(Set<Double> decryptedPrice){
        ui.displayPrices(decryptedPrice);
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