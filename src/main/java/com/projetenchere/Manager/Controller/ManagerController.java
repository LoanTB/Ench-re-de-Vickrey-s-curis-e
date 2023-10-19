package com.projetenchere.Manager.Controller;

import com.projetenchere.Manager.ManagerApp;
import com.projetenchere.Manager.View.IManagerUserInterface;
import com.projetenchere.Manager.View.commandLineInterface.ManagerCommandLineInterface;
import com.projetenchere.common.Model.Offer;
import com.projetenchere.common.Model.Bid;

import java.util.Arrays;
import java.util.List;


public class ManagerController {

    public static final IManagerUserInterface ui = new ManagerCommandLineInterface();


    //Creer l'enchère.
    /*
    public List<Bid> createBid()
    {

    }*/

    //Generer
    /*
    public void generateKeyPair()
    {

    }
    */

    //Lancer l'enchère donc l'envoyer aux Bidders + envoyer la clé publique.
    /*
    public void sendBid(List<Bid> bids, )
    {

    }*/

    //Recevoir les offres.

    /*
    public List<EncryptedOffer> fetchEncryptedOffers()
    {

    }
    */

    //Déchiffrer les offres.



    /*
    public static boolean checkWinner()
    {
        String confirmation = "";
        boolean waitConfirmation = true;
        Scanner scan = new Scanner(System.in);
        while(waitConfirmation)
        {
            System.out.println("Est-ce correct ? y/n");
            confirmation = scan.nextLine();
            confirmation = confirmation.replaceAll("\n","");
            confirmation = confirmation.toLowerCase();

            if (confirmation.startsWith("y"))
            {
                waitConfirmation=false;
                System.out.println("Ok.");
                return true;
            }
            if (confirmation.startsWith("n"))
            {
                waitConfirmation=false;
                System.out.println("Resort...");
                return false;
            }
        }
        return false;
    }*/

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