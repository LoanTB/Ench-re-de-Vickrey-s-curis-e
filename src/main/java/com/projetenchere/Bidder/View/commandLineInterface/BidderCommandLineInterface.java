package com.projetenchere.Bidder.View.commandLineInterface;

import com.projetenchere.Bidder.View.IBidderUserInterface;
import com.projetenchere.common.Model.Offer;

import java.util.Scanner;

public class BidderCommandLineInterface implements IBidderUserInterface {
    public static final Scanner scanner = new Scanner(System.in);

    public BidderCommandLineInterface() {
    }

    private void showMessage(String message) {
        System.out.println(message);
    }

    private String readMessage() {
        return scanner.nextLine();
    }

    @Override
    public void displayCurrentBid() {
        //TODO: fetch current bid from Authority

    }

    @Override
    public Offer readOffer() {
        String offerString = readMessage();
        return new Offer(offerString);
    }

    @Override
    public void tellOfferWon(int priceToPay) {

    }


    @Override
    public void tellOfferLost() {

    }
}
