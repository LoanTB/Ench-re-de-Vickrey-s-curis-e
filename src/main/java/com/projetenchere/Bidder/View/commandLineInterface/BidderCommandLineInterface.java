package com.projetenchere.Bidder.View.commandLineInterface;

import com.projetenchere.Bidder.Model.Bidder;
import com.projetenchere.Bidder.View.IBidderUserInterface;
import com.projetenchere.Bidder.View.graphicalUserInterface.BidderGraphicalUserInterface;
import com.projetenchere.common.Models.CurrentBids;
import com.projetenchere.common.Models.Offer;

import java.util.Scanner;

public class BidderCommandLineInterface implements IBidderUserInterface {
    public static final Scanner scanner = new Scanner(System.in);

    public void showMessage(String message) {
        System.out.println(message);
    }

    private String readMessage() {
        return scanner.nextLine();
    }

    private static BidderCommandLineInterface instance = null;

    public static BidderCommandLineInterface getInstance() {
        if (instance == null) {
            instance = new BidderCommandLineInterface();
        }
        return instance;
    }

    @Override
    public void displayBid(CurrentBids currentBids) {
        showMessage("Enchères Actuelle :");
        showMessage(currentBids.toString() + "\n");
    }

    @Override
    public Offer readOffer(Bidder bidder, CurrentBids currentBids) {
        showMessage("Quel est l'identifiant de l'enchère sur laquelle vous voulez enchérir ?");
        String idBidString = "";
        while (currentBids.getBid(idBidString) == null) {
            idBidString = readMessage();
            if (currentBids.getBid(idBidString) == null) {
                showMessage("Id invalide, entrez un id d'enchère valide :");
            }
        }
        showMessage("Quel est votre prix ?");
        String offerString = "";
        int offer = 0;
        while (offer < 0 || !offerString.matches("\\d+")) {
            offerString = readMessage();
            if (!offerString.matches("\\d+")) {
                showMessage("Prix invalide, entrez un prix sans caractère spéciaux :");
            }
            if (offer < 0) {
                showMessage("Prix invalide, entrez un prix positif :");
            }
        }
        return new Offer(idBidString, offerString);
    }

    @Override
    public void tellSignatureConfigSetup() {
        showMessage("Mise en place de la configuration de la signature...");
    }

    @Override
    public void tellSignatureConfigGeneration() {
        showMessage("Génération de la configuration de la signature ...");
    }

    @Override
    public void tellSignatureConfigReady() {
        showMessage("Configuration de la signature terminée.");
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
    public void tellOfferSent() {
        showMessage("Votre offre a bien été envoyée");
    }

    @Override
    public void tellWaitManagerSecurityInformations() {
        showMessage("Attente des informations de sécurité du gestionnaire...");
    }

    @Override
    public void displayHello() {
        showMessage("Bienvenue enchérisseur !");
    }

    @Override
    public void tellReceiptOfCurrentBids() {
        showMessage("Réception des enchères actuelles");
    }


}
