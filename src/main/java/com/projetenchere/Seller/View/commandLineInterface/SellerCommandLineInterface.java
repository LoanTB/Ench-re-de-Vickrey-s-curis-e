package com.projetenchere.Seller.View.commandLineInterface;

import com.projetenchere.Seller.View.ISellerUserInterface;
import com.projetenchere.common.Model.Encrypted.EncryptedOffer;

import java.util.Scanner;

public class SellerCommandLineInterface implements ISellerUserInterface {

    public static final Scanner scanner = new Scanner(System.in);

    public void showMessage(String message) {
        System.out.println(message);
    }

    private String readMessage() {
        return scanner.nextLine();
    }

    @Override
    public void displayWinner(String winnerID, Double price) {
        showMessage("Le grand gagnant est "+winnerID+" ! La mise à payé est de "+price);
    }

    @Override
    public void displayOfferReceived(EncryptedOffer encryptedOffer) {
        showMessage("Nouvelle offre reçue de "+encryptedOffer.getIdBidder()+".");
    }

    @Override
    public void displayEncryptedPriceSended() {
        showMessage("Prix chiffrés envoyés à l'autorité de gestion pour traitement...");
    }

    @Override
    public void displayResultsSent() {
        showMessage("Résultats envoyés aux enchérisseurs.");
    }

    @Override
    public void diplayHello() {
        showMessage("Bienvenue vendeur !");
    }


}
