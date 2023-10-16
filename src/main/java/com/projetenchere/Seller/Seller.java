package com.projetenchere.Seller;

/*------ Libraries ------*/
import java.util.Arrays;
import java.util.Random;

class Seller {

    /**
     * @param prices_cypher le tableau des prix chiffrés.
     * @return Le tableau prices_cypher mélangé.
     */
    public static int[] shuffleCypher(int prices_cypher[])
    {
        int[] prices_cypher_shuffled = prices_cypher.clone();
        System.out.println(Arrays.toString(prices_cypher_shuffled));
        Random rand = new Random();

        for (int i = 0; i < prices_cypher_shuffled.length ; i ++)
        {
            int randomIndexToSwap = rand.nextInt(prices_cypher_shuffled.length);
            int temp = prices_cypher_shuffled[randomIndexToSwap];
            prices_cypher_shuffled[randomIndexToSwap] = prices_cypher_shuffled[i];
            prices_cypher_shuffled[i] = temp;
        }
        System.out.println(Arrays.toString(prices_cypher_shuffled));

        return prices_cypher_shuffled;
    }

    /**
     * @param prices_cypher le tableau de prix chiffrés.
     * @param winner_price_and_cypher le tableau avec index 0 le chiffré et index 1 le prix.
     * @return la place de l'enchérisseur gagnant dans l'ordre de reception des prix.
     */
    public static int getBidderWinner(int[] prices_cypher,int[] winner_price_and_cypher)
    {
        int bidder_winner_reveived_place = 0;
        for (int i=0; i < prices_cypher.length ; i++)
        {
            if(prices_cypher[i] == winner_price_and_cypher[0])
            {
                bidder_winner_reveived_place = i;
                return bidder_winner_reveived_place;
            }
        }
        return bidder_winner_reveived_place;
    }

}