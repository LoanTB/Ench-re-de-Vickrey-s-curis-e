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
    private VBox messagesVBox;
    @FXML
    private ScrollPane scrollPane;

        public void addLogMessage(String message) {
            Platform.runLater(() -> {
                Label messageLabel = new Label(message);
                messagesVBox.getChildren().add(messageLabel);
                scrollPane.setVvalue(1.0);
            });
        }

    @FXML
    public void displayHello() {
        addLogMessage("Bienvenue Manager !");
    }

    @FXML
    private void handleTestLogButton() {
        addLogMessage("Message de test");
    }


    @Override
    public void tellSignatureConfigSetup() {

    }

    @Override
    public void tellSignatureConfigGeneration() {

    }

    @Override
    public void tellSignatureConfigReady() {

    }

    @Override
    public void displayGenerateKey() {

    }

    @Override
    public void displayBidLaunch() {

    }

    @Override
    public void displayReceivedPrices() {

    }

    @Override
    public void displayPriceProcessing() {

    }

    @Override
    public void displaySentWinnerPrice() {

    }

    @Override
    public void displayEndOfAuction() {

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

    }

    @Override
    public void tellConnectingNewSeller(String id) {

    }

    @Override
    public void tellConnectingNewBidder(String id) {

    }

    @Override
    public void tellRequestInformationAboutBiddersBySeller(String id) {

    }

    @Override
    public void tellRequestCurrentBidsByBidder(String id) {

    }

    @Override
    public void tellRequestToDetermineTheWinnerOfBidBySeller(String idBid, String idSeller) {

    }

    @Override
    public void tellManagerReadyToProcessBids() {

    }

    @Override
    public void tellBidReceivedby(String idSeller, String idBid) {

    }

    @Override
    public void tellReceivingAndReplyToInformationOf(String id, String type) {

    }
}
