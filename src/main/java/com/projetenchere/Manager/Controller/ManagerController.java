package com.projetenchere.Manager.Controller;

import com.projetenchere.Manager.ManagerApp;
import com.projetenchere.Manager.View.IManagerUserInterface;
import com.projetenchere.Manager.View.commandLineInterface.ManagerCommandLineInterface;
import com.projetenchere.common.Model.Offer;
import com.projetenchere.common.Model.Bid;
import com.projetenchere.common.Util.EncryptionUtil;

import java.security.KeyPair;
import java.security.PublicKey;
import java.util.List;


public class ManagerController {

    public static final IManagerUserInterface ui = new ManagerCommandLineInterface();

    //Generer
    public KeyPair generateManagerKeys() throws Exception {
        KeyPair ManagerKeys = EncryptionUtil.generateKeyPair();
        return ManagerKeys;
    }


    //Creer l'enchère.

    public Bid createBid()
    {
        return new Bid();
    }


    //Lancer l'enchère donc l'envoyer aux Bidders + envoyer la clé publique.

    public void sendBidAndKey(Bid currentBid, PublicKey managerPublicKey)
    {
        //TODO : SendObjects.
    }


    //Recevoir les offres.

    /*
    public List<EncryptedOffer> fetchEncryptedOffers()
    {
        //TODO : FetchObjects ?
    }
    */

    //Déchiffrer les offres.
/*
    public List<Offer> decryptEncryptedOffers(List<EncryptedOffer> ReceivedOffers){
        //TODO : Decrypt each offers.
        //return List<Offer> DecryptedOffers;

    }

    public float getWinnerPrice(List<Offer> DecryptedOffers)
    {
        //TODO : Adapt int[] to List<Offer>.
    }
*/

    /*
    private static int getWinnerPrice(int[] prices)
    {
        System.out.println("Original : ");
        for(int num : prices)
        {
            System.out.println(num + "");
        }
        Arrays.sort(prices); //Tri du plus petit au plus grand.
        System.out.println("Sorted : ");

        for(int num : prices)
        {
            System.out.println(num + "");
        }

        return prices[prices.length -2];
    }

    public static void priceProcessing(int[] BidderPrice)
    {
        boolean correct = false;
        while(!correct) {
            int winnerPrice = getWinnerPrice(BidderPrice);
            System.out.println("Winner price is : " + winnerPrice);
            correct = checkWinner();
        }
    }
    */

    /*
    public void showPrices(List<>){
        ui.displayPrices(List<>);
    }

    public void sendWinnerAndPrice()
    {

    }
     */

}