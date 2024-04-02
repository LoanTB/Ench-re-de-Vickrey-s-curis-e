package com.projetenchere.Seller.View.graphicalUserInterface;

import com.projetenchere.Seller.View.ISellerUserInterface;
import com.projetenchere.common.Models.Bid;
import com.projetenchere.common.View.UserGraphicalUserInterface;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
    public Label hLabel;
    @FXML
    public Label mLabel;
    @FXML
    public Label sLabel;
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
        minuteComboBox.getSelectionModel().select(Integer.valueOf((now.getMinute() + 1) % 60));
        secondComboBox.getSelectionModel().select(Integer.valueOf((now.getSecond() + 30) % 60));
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
            hLabel.setVisible(false);
            hLabel.setManaged(false);
            mLabel.setVisible(false);
            mLabel.setManaged(false);
            sLabel.setVisible(false);
            sLabel.setManaged(false);
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
    public void displayEncryptedOffersSet() {

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
        while (bid == null) {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return bid;
    }

    @Override
    public void displayBidCreated(Bid bid) {
        addLogMessage("Vous avez créé l'enchère : ");
        addLogMessage(bid.toString(false));
    }

    @Override
    public void tellSendBidToManager() {
        addLogMessage("Envoie de l'enchère au gestionnaire...");
    }


    @Override
    public void tellWaitForParticipation(){
        addLogMessage("Attente de participants.");
    }

    @Override
    public synchronized void tellNewParticipant(){
        addLogMessage("Nouvelle participation !");
    }

    @Override
    public synchronized void tellParticipationRejected(){
        addLogMessage("Une participation a été rejetée.");
    }

    @Override
    public void tellEndOfParticipation(){
        addLogMessage("Fin de la participation.");
    }

    @Override
    public void tellSendBiddersVerification(){
        addLogMessage("Envoie de la vérification auprès des enchérisseurs.");
    }

    @Override
    public void displayEndBid(String idBid){
        addLogMessage("L'enchère " + idBid + " est terminée.");
    }

    @Override
    public void tellSendResolutionToManager(){
        addLogMessage("Envoie de la demande de résolution au gestionnaire.");
    }

    @Override
    public void tellFalsifiedSignatureManager()
    {
        addLogMessage("Signature du gestionnaire usurpée ! Enchères compromises !");
    }

    @Override
    public void tellFalsifiedSignatureBidder() {
        addLogMessage("Signature de l'enchérisseur usurpée !");
    }

    @Override
    public void tellWaitWinnerManifestation(){
        addLogMessage("Attente qu'un gagnant se manifeste !");
    }

    @Override
    public void tellWinnerBid(double prix){
        addLogMessage("Le prix gagnant est " +prix+ "€");
    }

    @Override
    public void tellResultsSend(){
        addLogMessage("Résultats envoyés aux enchérisseurs.");
    }


}
