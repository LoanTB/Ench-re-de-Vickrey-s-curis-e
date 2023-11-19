package com.projetenchere.Manager.View.commandLineInterface;

import com.projetenchere.Manager.View.IManagerUserInterface;
import com.projetenchere.common.Models.Network.Communication.Winner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManagerCommandLineInterface implements IManagerUserInterface {
    public static final Scanner scanner = new Scanner(System.in);

    public ManagerCommandLineInterface() {}

    private static boolean isValidDateFormat(String value, DateTimeFormatter formatter) {
        try {
            LocalDateTime.parse(value, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public String readMessage(int maxLength) {
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
    public LocalDateTime askBidEndTime() {
        boolean checkType = true;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.now();
        String dateStr;
        while (checkType) {
            showMessage("Veuillez saisir la date de fin de l'enchère au format dd-MM-yyyy HH:mm:ss");
            dateStr = readMessage(19);
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
        return readMessage(50);
    }

    @Override
    public String askBidName() {
        int maxLength = 20;
        showMessage("Veuillez saisir le nom de l'enchère (taille max " + maxLength + " caractères) : ");
        return readMessage(maxLength);
    }

    @Override
    public void displayHello() {
        showMessage("Bienvenue Manager !");
    }

    @Override
    public void displayGenerateKey() {
        showMessage("Génération de clé...");
    }

    @Override
    public void displayBidLaunch() {
        showMessage("Lancement de l'enchère...");
    }

    @Override
    public void displayReceivedPrices() {
        showMessage("Réception des prix...");
    }

    @Override
    public void displayPriceProcessing() {
        showMessage("Traitement des prix...");
    }

    @Override
    public void displaySentWinnerPrice() {
        showMessage("Envoie du prix gagnant...");
    }

    @Override
    public void displayEndOfAuction() {
        showMessage("Fin des echères !");
    }

    @Override
    public int askBidId() {
        showMessage("Veuillez saisir l'id de l'enchère : ");
        return Integer.parseInt(scanner.nextLine()); // TODO : Verifier l'entrée utilisateur
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

            input = readMessage(15);
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
    public void displayWinnerPrice(Winner winner) {
        System.out.println("Prix gagnant : " + winner.getPrice());
    }

}
