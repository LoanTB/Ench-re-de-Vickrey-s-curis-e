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
        String offerString = readMessage();
        return new Offer(offerString);
    }

    @Override
    public void tellOfferWon(int priceToPay) {
        showMessage("Your offer won, you have to pay " + priceToPay + "€");
    }


    @Override
    public void tellOfferLost() {
        showMessage("Your offer lost");
    }

    @Override
    public void tellOfferAlreadySent() {
        showMessage("You have already sent an offer for this bid.");
    }
}
