package com.projetenchere.Seller.View.commandLineInterface;

import com.projetenchere.Seller.View.ISellerUserInterface;
import com.projetenchere.common.Models.Encrypted.EncryptedOffer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SellerCommandLineInterface implements ISellerUserInterface {

    public static final Scanner scanner = new Scanner(System.in);

    public void showMessage(String message) {
        System.out.println(message);
    }

    private String readMessage() {
        return scanner.nextLine();
    }

    public String readMessageWithMaxLen(int maxLength) {
        String input = "";
        boolean askinput = true;
        while (askinput) {
            input = scanner.nextLine();
            if (input.length() > maxLength) {
                System.err.println("Le champ dépasse la taille maximale autorisée. Veuillez réessayer.");
            }
            if (input.length() <= 2) {
                System.err.println("Entrée trop courte. Veuillez réessayer.");
            } else {
                askinput = false;
            }
        }
        return input;
    }

    @Override
    public void displayWinner(String winnerID, Double price) {
        showMessage("Le grand gagnant est "+winnerID+" ! La mise à payer est de "+price+"€.");
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

    @Override
    public void displayBidReceived(String bid){
        showMessage("Enchère reçue :");
        showMessage(bid+"\n");
    }

    @Override
    public void waitOffers(){
        showMessage("Attente d'offres...");
    }

    @Override
    public String askSellerAddress() {
        String input = "";
        boolean askAddress = true;

        String ipAddressPattern = "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";

        Pattern pattern = Pattern.compile(ipAddressPattern);

        while (askAddress) {
            showMessage("Veuillez saisir l'addresse IP  du vendeur au format xxx.xxx.xxx.xxx  : ");

            input = readMessageWithMaxLen(15);
            Matcher matcher = pattern.matcher(input);
            if (!matcher.matches()) {
                showMessage("Adresse IP invalide. Veuillez réessayer.");
            } else {
                askAddress = false;
            }
        }
        return input;
    }

    @Override
    public int askSellerPort() {
        showMessage("Veuillez saisir le port que vous voulez utiliser : ");
        return Integer.parseInt(scanner.nextLine()); // TODO : Verifier l'entrée utilisateur avec isValidInt
    }

    private static boolean isValidDateFormat(String value, DateTimeFormatter formatter) {
        try {
            LocalDateTime.parse(value, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public LocalDateTime askBidEndTime() {
        boolean checkType = true;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.now();
        String dateStr;
        while (checkType) {
            showMessage("Veuillez saisir la date de fin de l'enchère au format dd-MM-yyyy HH:mm:ss");
            dateStr = readMessageWithMaxLen(19);
            if (isValidDateFormat(dateStr, formatter)) {

                dateTime = LocalDateTime.parse(dateStr, formatter);
                if (dateTime.isAfter(LocalDateTime.now())) {
                    checkType = false;
                } else {
                    System.err.println("Erreur : La date de fin ne peut pas être dans le passé.");
                }

            } else {
                System.err.println("Erreur : Mauvais format de date saisi.");
            }
        }
        return dateTime;
    }

    @Override
    public String askBidDescription() {
        showMessage("Veuillez saisir la description de l'enchère : ");
        return readMessage();
    }

    @Override
    public String askBidName() {
        showMessage("Veuillez saisir le nom de l'enchère : ");
        return readMessage();
    }


    private static boolean isValidInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public int askBidId() {
        showMessage("Veuillez saisir l'id de l'enchère : ");
        return Integer.parseInt(scanner.nextLine()); // TODO : Verifier l'entrée utilisateur avec isValidInt
    }
}
