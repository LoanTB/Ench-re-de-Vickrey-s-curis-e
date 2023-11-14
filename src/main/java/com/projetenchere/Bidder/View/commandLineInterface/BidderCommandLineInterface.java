package com.projetenchere.Bidder.View.commandLineInterface;

import com.projetenchere.Bidder.Model.Bidder;
import com.projetenchere.Bidder.View.IBidderUserInterface;
import com.projetenchere.common.Models.Bid;
import com.projetenchere.common.Models.Offer;

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
        showMessage(bid._toString()+"\n");
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
        showMessage("Quel port voulez-vous utiliser ? (49152 à 65535)");
        String portString = readMessage();
        int port = Integer.parseInt(portString);
        while (port < 49152 || port > 65535){
            showMessage("Port invalide, entrez un port valide (entre 49152 et 65535) :");
            portString = readMessage();
            port = Integer.parseInt(portString);
        }
        return port;
    }


}
