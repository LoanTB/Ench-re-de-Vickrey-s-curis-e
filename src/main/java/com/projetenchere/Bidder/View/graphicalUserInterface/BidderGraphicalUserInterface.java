package com.projetenchere.Bidder.View.graphicalUserInterface;

import com.projetenchere.Bidder.Model.Bidder;
import com.projetenchere.Bidder.View.IBidderUserInterface;
import com.projetenchere.common.Models.CurrentBids;
import com.projetenchere.common.Models.Offer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class BidderGraphicalUserInterface implements IBidderUserInterface {

    @FXML
    private VBox messagesVBox = new VBox();
    @FXML
    private ScrollPane scrollPane = new ScrollPane();

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

    @Override
    public void displayBid(CurrentBids currentBids) {
        addLogMessage("Enchères Actuelle :");
        addLogMessage(currentBids.toString()+"\n");
    }

    @Override
    public Offer readOffer(Bidder bidder, CurrentBids currentBids) {
        return null;
    }

    @Override
    public void tellSignatureConfigSetup(){
        addLogMessage("Mise en place de la configuration de la signature...");
    }
    @Override
    public void tellSignatureConfigGeneration(){
        addLogMessage("Génération de la configuration de la signature ...");
    }
    @Override
    public void tellSignatureConfigReady(){
        addLogMessage("Configuration de la signature terminée.");
    }

    @Override
    public void tellOfferWon(double priceToPay) {
        addLogMessage("Votre offre a gagné, vous devez payer " + priceToPay + "€");
    }

    @Override
    public void tellOfferLost() {
        addLogMessage("Votre offre a perdu");
    }

    @Override
    public void tellOfferSent() {
        addLogMessage("Votre offre a bien été envoyé");
    }

    @Override
    public void tellWaitOfferResult() {
        addLogMessage("Attente des résultats...");
    }

    @Override
    public void tellWaitBidsAnnoncement() {
        addLogMessage("Attente de reception des enchères en cours...");
    }

    @Override
    public void tellWaitBidsPublicKeysAnnoncement() {
        addLogMessage("Attente/Verification de reception des clés des enchères en cours...");
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

    }

    @Override
    public void tellManagerFound() {

    }

    @Override
    public void tellWaitManagerSecurityInformations() {
        addLogMessage("Attente des informations de sécurité du gestionnaire...");
    }

    @Override
    public void displayHello() {
        addLogMessage("Bienvenue Enchérisseur !");
    }

    @Override
    public void tellReceivingInformationOf(String id, String type) {
        addLogMessage("Reception d'information de "+type+" "+id);
    }

    @Override
    public void tellReceiptOfCurrentBids() {
        addLogMessage("Reception des enchères actuelles");
    }

    @Override
    public void tellReceiptOfEncryptionKeysForCurrentBids() {
        addLogMessage("Reception des clés de chiffrement des enchères actuelles");
    }

    @Override
    public void tellReceiptOfBidResult(String id) {
        addLogMessage("Reception des résultats de l'enchère "+id);
    }

    @Override
    public void tellSendRequestOffers() {
        addLogMessage("Envoie de la demande d'enchères actuelles au gestionnaire");
    }
}
