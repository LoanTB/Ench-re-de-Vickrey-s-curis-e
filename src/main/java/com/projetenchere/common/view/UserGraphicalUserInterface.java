package com.projetenchere.common.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.UUID;

public abstract class UserGraphicalUserInterface implements IUserInterface {
    protected final String instanceId = UUID.randomUUID().toString();
    protected Stage primaryStage;
    @FXML
    protected ScrollPane scrollPane = new ScrollPane();
    @FXML
    private VBox messagesVBox = new VBox();

    protected UserGraphicalUserInterface() {
        System.out.println("Interface instance created: " + instanceId);
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public synchronized void addLogMessage(String message) {
        Platform.runLater(() -> {
            Label messageLabel = new Label(message);
            messagesVBox.getChildren().add(messageLabel);
            scrollPane.setVvalue(1.0);
        });
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
}
