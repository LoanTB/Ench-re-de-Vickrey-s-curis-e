package com.projetenchere.Manager;

/*--- Java libraries ---*/
import java.io.*;
import java.util.*;

class Manager {

/*------ Auction launch ------*/
    //Creer l'enchère.

    //Lancer l'enchère donc l'envoyer aux Bidders + envoyer la clé publique.

/*------ Offer received ------*/

    //Recevoir les offres.

/*------ Price processing ------*/

    //Déchiffrer les offres.

    /**
     *
     * @return boolean, false if the manager say "n" and true if it say "y".
     */
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
    }

    /**
     *
     * @param prices to sort.
     * @return winner price.
     */
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

    /**
     *
     * @param BidderPrice
     */
    public static void priceProcessing(int[] BidderPrice)
    {
        boolean correct = false;
        while(!correct) {
            int winnerPrice = getWinnerPrice(BidderPrice);
            System.out.println("Winner price is : " + winnerPrice);
            correct = checkWinner();
        }
    }

}