package com.projetenchere.Manager.Controller;

import com.projetenchere.Manager.ManagerApp;
import com.projetenchere.Manager.View.IManagerUserInterface;
import com.projetenchere.Manager.View.commandLineInterface.ManagerCommandLineInterface;
import com.projetenchere.common.Model.BidStarter;
import com.projetenchere.common.Model.Offer;
import com.projetenchere.common.Model.Bid;
import com.projetenchere.common.Model.Encrypted.EncryptedOffer;
import com.projetenchere.common.Util.EncryptionUtil;

import java.security.KeyPair;
import java.security.PublicKey;
import java.util.List;
import java.util.ArrayList;

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
        return ui.askBidInformations();
    }

    //Lancer l'enchère donc l'envoyer aux Bidders + envoyer la clé publique.

    public void sendBidAndKey(Bid currentBid, PublicKey managerPublicKey)
    {
        BidStarter currentBidStarter = new BidStarter(managerPublicKey,currentBid);

        //TODO : SendObjects. ????
    }


    public void showPrices(List<Offer> DecryptedOffer){
        ui.displayPrices(DecryptedOffer);
    }


    //Recevoir les offres.
    public void fetchEncryptedOffers() //ADD : Retour : List<EncryptedOffer>
    {
        //TODO : FetchObjects ?
    }

    //Déchiffrer les offres.
    public void decryptEncryptedOffers(List<EncryptedOffer> ReceivedOffers){ //ADD : retour List<Offer>
        //TODO : Decrypt each offers.
        return ;
    }

    public float getWinnerPrice(List<Offer> DecryptedOffer)
    {
        //TODO : Adapt int[] to List<Offer>.
        return 1;
    }

/*
    private static int[] getWinnerPrice(int[] prices_cypher_shuffled)
    {
        int prices_decode[] = testDecode(prices_cypher_shuffled);
        int nBidder = prices_decode.length;

        System.out.println("Testgetwinner cypher shuffled" + Arrays.toString(prices_cypher_shuffled));
        System.out.println("Testgetwinner prices decode " + Arrays.toString(prices_decode));


        System.out.println("Original : ");
        for(int num : prices_decode)
        {
            System.out.println(num + "");
        }

        int[] prices_decode_sorted = prices_decode.clone();

        Arrays.sort(prices_decode_sorted); //Tri du plus petit au plus grand.
        System.out.println("Sorted : ");

        for(int num : prices_decode_sorted)
        {
            System.out.println(num + "");
        }

        int winnerPrice = prices_decode_sorted[nBidder -2];
        int winnerCypher = 0;
        for (int i = 0; i < nBidder ; i++)
        {
            if(prices_decode[i] == winnerPrice)
            {
                winnerCypher = prices_cypher_shuffled[i];
                int[] winner_price_and_cypher = {winnerCypher, winnerPrice};
                return winner_price_and_cypher;
            }
        }

        int[] winner_price_and_cypher = {winnerCypher, winnerPrice};
        return winner_price_and_cypher;
    }*/

    public void sendWinnerAndPrice()
    {
        //TODO : Envoyer le chiffré du gagnant (plus grand prix chiffré), et le prix gagnant.
    }

}