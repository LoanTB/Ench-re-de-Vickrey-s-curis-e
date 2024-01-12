package com.projetenchere.Seller.View.commandLineInterface;

import com.projetenchere.Seller.View.ISellerUserInterface;
import com.projetenchere.common.Models.Bid;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.UUID;

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
    public void displayOfferReceived() {
        showMessage("Nouvelle offre reçue.");
    }

    @Override
    public void displayEncryptedOffersSetent() {
        showMessage("Prix chiffrés envoyés à l'autorité de gestion pour traitement...");
    }

    @Override
    public void displayResultsSent() {
        showMessage("Résultats envoyés aux enchérisseurs.");
    }

    @Override
    public void displayHello() {
        showMessage("Bienvenue vendeur !");
    }

    @Override
    public void tellSignatureConfigSetup(){
        showMessage("Mise en place de la configuration de la signature...");
    }
    @Override
    public void tellSignatureConfigGeneration(){
        showMessage("Génération de la configuration de la signature ...");
    }
    @Override
    public void tellSignatureConfigReady(){
        showMessage("Configuration de la signature terminée.");
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
    public int readPort() {
        showMessage("Quel port voulez-vous utiliser ? (49152 à 65535)");
        String portString = "";
        int port = 0;
        while (port < 49152 || port > 65535 || !portString.matches("\\d+") ){
            portString = readMessage();
            if(portString.matches("\\d+")){
                port = Integer.parseInt(portString);
                if(port < 49152 || port > 65535){
                    showMessage("Port invalide, entrez un port valide (entre 49152 et 65535) :");
                }
            }else {
                showMessage("Port invalide, entrez un port sans lettres :");
            }
        }
        return port;
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
    public Bid askBid() {
        String id = UUID.randomUUID().toString();
        String name = askBidName();
        String description = askBidDescription();
        LocalDateTime end = askBidEndTime();
        return new Bid(id, name, description, end);
    }

    @Override
    public void tellWaitManagerSecurityInformations() {
        showMessage("Attente des informations de sécurité du gestionnaire...");
    }

    @Override
    public void tellWaitWinnerDeclaration() {
        showMessage("Attente des resultats de l'enchère...");
    }

    @Override
    public void displayBidCreated(Bid bid) {
        showMessage("Vous avez créé l'enchère : ");
        showMessage(bid.toString(false));
    }

    @Override
    public void tellReceivingInformationOf(String id, String type) {
        showMessage("Reception d'information de "+type+" "+id);
    }

    @Override
    public void tellReceiptOfferByBidder(String id) {
        showMessage("Reception d'une offre de l'enchérisseur "+id);
    }

    @Override
    public void tellReceiptBidResult(String id) {
        showMessage("Reception des résultats de l'enchère "+id);
    }

    @Override
    public void tellSendBidToManager() {
        showMessage("Envoie de l'enchère au manager...");
    }

    @Override
    public void tellManagerConfirmsReceipt() {
        showMessage("Le gestionnaire confirme la reception");
    }

    @Override
    public void tellSuccessfulSecuringOfTheCommunicationChannelWithTheManager() {
        showMessage("Sécurisation du canal de communication avec le gestionnaire réussie, demande des enchérisseurs potentiels actuels");
    }

    @Override
    public void tellWaitManager() {
        showMessage("Le gestionnaire des enchères semble indisponible, attente du gestionnaire...");
    }

    @Override
    public void tellManagerFound() {
        showMessage("Contact avec le gestionnaire établi !");
    }

    @Override
    public String readName() {
        showMessage("Quel est votre prénom ?");
        return readMessage();
    }

    @Override
    public String readSurname() {
        showMessage("Quel est votre nom ?");
        return readMessage();
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
}
