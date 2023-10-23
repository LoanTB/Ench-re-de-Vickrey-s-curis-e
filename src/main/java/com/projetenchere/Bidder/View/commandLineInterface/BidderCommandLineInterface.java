package com.projetenchere.Bidder.View.commandLineInterface;

import com.projetenchere.Bidder.Model.Bidder;
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
        showMessage("Bienvenue!");
        showMessage("Enchère Actuelle :");
        showMessage(bid._toString());

    }

    @Override
    public Offer readOffer(Bidder bidder) {
        showMessage("Quel est votre prix ?");
        String offerString = readMessage();
        return new Offer(bidder.getId(), offerString);
    }

    @Override
    public void tellOfferWon(double priceToPay) {
        showMessage("Votre offre a gagné, vous devez payer " + priceToPay + "€");
    }


    @Override
    public void tellOfferLost() {
        showMessage("Votre offre a perdu");
    }

    @Override
    public void tellOfferSent(){
        showMessage("Votre offre a bien été envoyé");
    }

    @Override
    public void tellWaitOfferResult(){
        showMessage("Attente des résultats...");
    }

    @Override
    public String readName() {
        showMessage("Quel est votre nom ?");
        return readMessage();
    }

    @Override
    public int readPort() {
        showMessage("Quel port voulez-vous utiliser ?");
        String portString = readMessage();
        return Integer.parseInt(portString);
    }


}
