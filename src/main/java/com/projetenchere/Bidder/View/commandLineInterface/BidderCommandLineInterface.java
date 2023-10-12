package com.projetenchere.Bidder.View.commandLineInterface;

import com.projetenchere.Bidder.View.IBidderUserInterface;
import com.projetenchere.common.Model.Offer;

import java.util.Scanner;

public class BidderCommandLineInterface implements IBidderUserInterface {
    public static final Scanner scanner = new Scanner(System.in);

    public BidderCommandLineInterface() {
    }

    @Override
    public void displayCurrentBid() {

    }

    @Override
    public Offer readOffer() {
        String offerString = scanner.nextLine();
        return new Offer(offerString);
    }

    @Override
    public void tellOfferWon(int priceToPay) {

    }

    @Override
    public void tellOfferLost() {

    }
    //TODO
}
