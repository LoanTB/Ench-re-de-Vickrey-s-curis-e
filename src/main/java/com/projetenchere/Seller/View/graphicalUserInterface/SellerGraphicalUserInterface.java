package com.projetenchere.Seller.View.graphicalUserInterface;

import com.projetenchere.Seller.View.ISellerUserInterface;
import com.projetenchere.common.Models.Bid;
import com.projetenchere.common.View.UserGraphicalUserInterface;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.UUID;

import static java.lang.Thread.sleep;

public class SellerGraphicalUserInterface extends UserGraphicalUserInterface implements ISellerUserInterface {

    @FXML
    public Button buttonCreate;
    @FXML
    public Label createBidVBoxTitle;
    @FXML
    public Label labelVBoxTitle;
    @FXML
    private TextField bidNameTextField = new TextField();
    @FXML
    private TextField bidDescriptionTextField = new TextField();
    @FXML
    private DatePicker endDatePicker = new DatePicker();
    @FXML
    private ComboBox<Integer> hourComboBox = new ComboBox<>();
    @FXML
    private ComboBox<Integer> minuteComboBox = new ComboBox<>();
    @FXML
    private ComboBox<Integer> secondComboBox = new ComboBox<>();

    private Bid bid = null;

    @FXML
    public void initialize() {
        for (int i = 0; i < 24; i++) {
            hourComboBox.getItems().add(i);
        }
        for (int i = 0; i < 60; i++) {
            minuteComboBox.getItems().add(i);
            secondComboBox.getItems().add(i);
        }
        LocalDate currentDate = LocalDate.now();
        endDatePicker.setValue(currentDate);
        LocalTime now = LocalTime.now();
        hourComboBox.getSelectionModel().select(Integer.valueOf(now.getHour()));
        minuteComboBox.getSelectionModel().select(Integer.valueOf((now.getMinute()+1)%60));
        secondComboBox.getSelectionModel().select(Integer.valueOf((now.getSecond()+30)%60));
    }

    @FXML
    private void handleCreateBidButton() {
        String bidName = bidNameTextField.getText();
        String bidDescription = bidDescriptionTextField.getText();

        LocalDate endDate = endDatePicker.getValue();
        Integer hour = hourComboBox.getValue();
        Integer minute = minuteComboBox.getValue();
        Integer second = secondComboBox.getValue();
        LocalDateTime bidEndDateTime = null;

        try {
            if (endDate != null && hour != null && minute != null && second != null) {
                bidEndDateTime = LocalDateTime.of(endDate, LocalTime.of(hour, minute, second));
                if (LocalDateTime.now().isAfter(bidEndDateTime)) {
                    bidEndDateTime = null;
                    addLogMessage("Erreur : La date de fin ne peut pas être dans le passé.");
                }
            }
        } catch (DateTimeParseException e) {
            addLogMessage("Erreur : La date n'est pas valide.");
        }

        if (bidName.isEmpty() || bidDescription.isEmpty() || bidEndDateTime == null) {
            addLogMessage("Erreur : Veuillez remplir tous les champs correctement.");
        } else {
            endDatePicker.setVisible(false);
            endDatePicker.setManaged(false);
            hourComboBox.setVisible(false);
            hourComboBox.setManaged(false);
            minuteComboBox.setVisible(false);
            minuteComboBox.setManaged(false);
            secondComboBox.setVisible(false);
            secondComboBox.setManaged(false);
            bidNameTextField.setVisible(false);
            bidNameTextField.setManaged(false);
            bidDescriptionTextField.setVisible(false);
            bidDescriptionTextField.setManaged(false);
            buttonCreate.setVisible(false);
            buttonCreate.setManaged(false);
            createBidVBoxTitle.setVisible(false);
            createBidVBoxTitle.setManaged(false);
            bid = new Bid(UUID.randomUUID().toString(), bidName, bidDescription, bidEndDateTime);
        }
    }


    @Override
    public void displayHello() {
        addLogMessage("Bienvenue Vendeur !");
    }

    @Override
    public void tellSignatureConfigSetup() {
        addLogMessage("Mise en place de la configuration de la signature...");
    }

    @Override
    public void tellSignatureConfigGeneration() {
        addLogMessage("Génération de la configuration de la signature ...");
    }

    @Override
    public void tellSignatureConfigReady() {
        addLogMessage("Configuration de la signature terminée.");
    }

    @Override
    public void displayWinner(String winnerID, Double price) {
        addLogMessage("Le grand gagnant est "+winnerID+" ! La mise à payer est de "+price+"€.");
    }

    @Override
    public void displayOfferReceived() {
        addLogMessage("Nouvelle offre reçue.");
    }

    @Override
    public void displayEncryptedOffersSet() {

    }

    public void displayEncryptedOffersSetent() {
        addLogMessage("Prix chiffrés envoyés à l'autorité de gestion pour traitement...");
    }

    @Override
    public void displayResultsSent() {
        addLogMessage("Résultats envoyés aux enchérisseurs.");
    }

    @Override
    public void displayBidReceived(String bid) {
        addLogMessage("Enchère reçue :");
        addLogMessage(bid+"\n");
    }

    @Override
    public void waitOffers() {
        addLogMessage("Attente d'offres...");
    }

    @Override
    public String askBidName() {
        return bidNameTextField.getText();
    }

    @Override
    public String askBidDescription() {
        return bidDescriptionTextField.getText();
    }

    @Override
    public Bid askBid() {
        while (bid==null){
            try {
                sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return bid;
    }

    @Override
    public String readName() {
        return null;
    }

    @Override
    public String readSurname() {
        return null;
    }

    @Override
    public int readPort() {
        return 0;
    }

    @Override
    public void tellWaitManager() {
        addLogMessage("Le gestionnaire des enchères semble indisponible, attente du gestionnaire...");
    }

    @Override
    public void tellManagerFound() {
        addLogMessage("Contact avec le gestionnaire établi !");
    }

    @Override
    public void tellWaitManagerSecurityInformations() {
        addLogMessage("Attente des informations de sécurité du gestionnaire...");
    }

    @Override
    public void tellWaitWinnerDeclaration() {
        addLogMessage("Attente des resultats de l'enchère...");
    }

    @Override
    public void displayBidCreated(Bid bid) {
        addLogMessage("Vous avez créé l'enchère : ");
        addLogMessage(bid.toString(false));
    }

    @Override
    public void tellSuccessfulSecuringOfTheCommunicationChannelWithTheManager() {
        addLogMessage("Sécurisation du canal de communication avec le gestionnaire réussie, demande des enchérisseurs potentiels actuels");
    }

    @Override
    public void tellReceivingInformationOf(String id, String type) {
        addLogMessage("Réception d'information de "+type+" "+id);
    }

    @Override
    public void tellReceiptOfferByBidder(String id) {
        addLogMessage("Réception d'une offre de l'enchérisseur "+id);
    }

    @Override
    public void tellReceiptBidResult(String id) {
        addLogMessage("Réception des résultats de l'enchère "+id);
    }

    @Override
    public void tellSendBidToManager() {
        addLogMessage("Envoie de l'enchère au gestionnaire...");
    }

    @Override
    public void tellManagerConfirmsReceipt() {
        addLogMessage("Le gestionnaire confirme la recéption");
    }
}
