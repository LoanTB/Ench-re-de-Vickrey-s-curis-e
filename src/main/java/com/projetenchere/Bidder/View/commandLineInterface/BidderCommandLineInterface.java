package com.projetenchere.Bidder.View.commandLineInterface;

import com.projetenchere.Bidder.View.IBidderUserInterface;
import com.projetenchere.common.Model.Bid;
import com.projetenchere.common.Model.Offer;

import java.util.Scanner;

public class BidderCommandLineInterface implements IBidderUserInterface {
    public static final Scanner scanner = new Scanner(System.in);

    public BidderCommandLineInterface() {
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    private String readMessage() {
        return scanner.nextLine();
    }

    @Override
    public void displayBid(Bid bid) {
        showMessage(bid._toString());

    }

    @Override
    public Offer readOffer() {
        showMessage("Quelle est votre prix");
        String offerString = readMessage();
        return new Offer("ID_Bidder",offerString);// TODO : Change ID_Bidder by the real name of the bidder
    }

    @Override
    public void tellOfferWon(int priceToPay) {
        showMessage("Votre offre a gagné, vous devez payer " + priceToPay + "€");
    }


    @Override
    public void tellOfferLost() {
        showMessage("Votre offre a perdu");
    }


}
