package com.projetenchere.Seller.View.graphicalUserInterface;

import com.projetenchere.Seller.View.ISellerUserInterface;
import com.projetenchere.common.Models.Encrypted.EncryptedOffer;
import com.projetenchere.common.Models.Bid;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class SellerGraphicalUserInterface implements ISellerUserInterface {


    @FXML
    private VBox messagesVBox = new VBox();
    @FXML
    private ScrollPane scrollPane = new ScrollPane();
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

    public void addLogMessage(String message) {
        Platform.runLater(() -> {
            Label messageLabel = new Label(message);
            messagesVBox.getChildren().add(messageLabel);
            scrollPane.setVvalue(1.0);
        });
    }

    @FXML
    public void handleTestLogButton() {
        addLogMessage("Message de test");
    }

    //@FXML
    //private void handleCreateBidButton() {
        //String bidName = bidNameTextField.getText();
        //String bidDescription = bidDescriptionTextField.getText();
        //LocalDateTime bidEndDateTime = getBidEndDateTime();
    //}

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
        return null;
    }

    @Override
    public String askBidDescription() {
        return null;
    }

    @Override
    public LocalDateTime askBidEndTime() {
        LocalDate endDate = endDatePicker.getValue();
        Integer hour = hourComboBox.getValue();
        Integer minute = minuteComboBox.getValue();
        Integer second = secondComboBox.getValue();

        if (endDate != null && hour != null && minute != null && second != null) {
            return LocalDateTime.of(endDate, LocalTime.of(hour, minute, second));
        }
        return null;
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
        addLogMessage("Reception d'information de "+type+" "+id);
    }

    @Override
    public void tellReceiptOfferByBidder(String id) {
        addLogMessage("Reception d'une offre de l'enchérisseur "+id);
    }

    @Override
    public void tellReceiptBidResult(String id) {
        addLogMessage("Reception des résultats de l'enchère "+id);
    }

    @Override
    public void tellSendBidToManager() {
        addLogMessage("Envoie de l'enchère au manager...");
    }

    @Override
    public void tellManagerConfirmsReceipt() {
        addLogMessage("Le gestionnaire confirme la reception");
    }
}
