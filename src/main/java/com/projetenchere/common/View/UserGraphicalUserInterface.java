package com.projetenchere.common.View;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.UUID;

public class UserGraphicalUserInterface {

    private static UserGraphicalUserInterface instance = null;
    protected final String instanceId = UUID.randomUUID().toString();
    protected Stage primaryStage;
    @FXML
    protected ScrollPane scrollPane = new ScrollPane();
    @FXML
    private VBox messagesVBox = new VBox();

    public UserGraphicalUserInterface() {
        System.out.println("Interface instance created: " + instanceId);
    }

    public static UserGraphicalUserInterface getInstance() {
        if (instance == null) {
            throw new NullPointerException("Instance non initialisée");
        }
        return instance;
    }

    public static void setInstance(UserGraphicalUserInterface instance) {
        UserGraphicalUserInterface.instance = instance;
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
}
