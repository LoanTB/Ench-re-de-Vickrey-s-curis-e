package com.projetenchere.Manager.View.graphicalUserInterface;

import com.projetenchere.Manager.View.IManagerUserInterface;
import com.projetenchere.common.Models.Bid;
import com.projetenchere.common.Models.Winner;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;



import java.awt.*;
import java.time.LocalDateTime;

public class ManagerGraphicalUserInterface implements IManagerUserInterface {


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

    @FXML
    public void displayHello() {
        addLogMessage("Bienvenue Manager !");
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
    public void displayGenerateKey() {
        addLogMessage("Génération de clé...");
    }

    @Override
    public void displayBidLaunch() {
        addLogMessage("Lancement de l'enchère...");
    }

    @Override
    public void displayReceivedPrices() {
        addLogMessage("Réception des prix...");
    }

    @Override
    public void displayPriceProcessing() {
        addLogMessage("Traitement des prix...");
    }

    @Override
    public void displaySentWinnerPrice() {
        addLogMessage("Envoie du prix gagnant...");
    }

    @Override
    public void displayEndOfAuction() {
        addLogMessage("Fin des echères !");
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
        return null;
    }

    @Override
    public String askSellerAddress() {
        return null;
    }

    @Override
    public void displayWinnerPrice(Winner winner) {
        addLogMessage("Le prix gagnant de l'enchère "+winner.bidId() + " à été déterminé et répondu (" + winner.price()+"€)");
    }

    @Override
    public void tellConnectingNewSeller(String id) {
        addLogMessage("Connexion avec un nouveau vendeur ("+id+")");
    }

    @Override
    public void tellConnectingNewBidder(String id) {
        addLogMessage("Connexion d'un nouvel enchérisseur ("+id+"), notification des vendeurs pour prévenir d'un nouvel enchérisseur potentiel");
    }

    @Override
    public void tellRequestInformationAboutBiddersBySeller(String id) {
        addLogMessage("Demande des informations sur les enchérisseurs actuellement connectés par le vendeur "+id);
    }

    @Override
    public void tellRequestCurrentBidsByBidder(String id) {
        addLogMessage("Demande des enchères actuelles par l'enchérisseur "+id);
    }

    @Override
    public void tellRequestToDetermineTheWinnerOfBidBySeller(String idBid, String idSeller) {
        addLogMessage("Demande de détermination du gagnant de l'enchère "+idBid+" par le vendeur "+idSeller);
    }

    @Override
    public void tellManagerReadyToProcessBids() {
        addLogMessage("Gestionnaire prêt à traiter des enchères");
    }

    @Override
    public void tellBidReceivedby(String idSeller, String idBid) {
        addLogMessage("Enchère reçu ("+idBid+") créé par le vendeur "+idSeller);
    }

    @Override
    public void tellReceivingAndReplyToInformationOf(String id, String type) {
        addLogMessage("Etablissement d'une connexion sécurisé avec un nouvel "+type+" ("+id+")");
    }
}
