package com.projetenchere.seller.view.commandLineInterface;

import com.projetenchere.seller.view.ISellerUserInterface;
import com.projetenchere.common.model.AbstractUserInterface;
import com.projetenchere.common.model.Bid;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.UUID;

public class SellerCommandLineInterface extends AbstractUserInterface implements ISellerUserInterface {

    private static boolean isValidDateFormat(String value, DateTimeFormatter formatter) {
        try {
            LocalDateTime.parse(value, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
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
    public void displayEncryptedOffersSet() {
        showMessage("Prix chiffrés envoyés à l'autorité de gestion pour traitement...");
    }

    @Override
    public void displayHello() {
        showMessage("Bienvenue vendeur !");
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
    public void displayBidCreated(Bid bid) {
        showMessage("Vous avez créé l'enchère : ");
        showMessage(bid.toString(false));
    }

    @Override
    public void tellSendBidToManager() {
        showMessage("Envoi de l'enchère au manager...");
    }

    @Override
    public void tellWaitForParticipation() {

    }

    @Override
    public void tellNewParticipant() {

    }

    @Override
    public void tellParticipationRejected() {

    }

    @Override
    public void tellEndOfParticipation() {

    }

    @Override
    public void tellSendBiddersVerification() {

    }

    @Override
    public void tellSendResolutionToManager() {

    }

    @Override
    public void tellFalsifiedSignatureManager() {

    }

    @Override
    public void tellFalsifiedSignatureBidder() {

    }

    @Override
    public void tellWinnerBid(double prix) {

    }

    @Override
    public void tellWaitWinnerManifestation() {

    }

    @Override
    public void displayEndBid(String idBid) {

    }

    @Override
    public void showNewOfferAlert() {
        showMessage("Nouvelle offre reçue !");
    }

    @Override
    public void tellBidEnd() {
        showMessage("Enchère finie !");
    }

    @Override
    public void tellSendOffersToManager() {
        showMessage("Envoie de la demande de résolution au gestionnaire.");
    }

    @Override
    public void displayWinnerPrice(double price) {
        showMessage("Le prix gagnant est " + price + "€");
    }

    @Override
    public void tellResultsSend() {
        showMessage("Résultats envoyés aux enchérisseurs.");
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

}
