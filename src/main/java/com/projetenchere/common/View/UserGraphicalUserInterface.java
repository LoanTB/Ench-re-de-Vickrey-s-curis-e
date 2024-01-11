package com.projetenchere.common.View;

import com.projetenchere.Manager.View.graphicalUserInterface.ManagerGraphicalUserInterface;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.UUID;

public class UserGraphicalUserInterface {

    private static UserGraphicalUserInterface instance = null;
    protected Stage primaryStage;

    public static UserGraphicalUserInterface getInstance(){
        if (instance == null){
            throw new NullPointerException("Instance non initialisée");
        }
        return instance;
    }

    public static void setInstance(UserGraphicalUserInterface instance){
        UserGraphicalUserInterface.instance = instance;
    }

    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    protected final String instanceId = UUID.randomUUID().toString();

    public UserGraphicalUserInterface() {
        System.out.println("Interface instance created: " + instanceId);
    }

    @FXML
    private VBox messagesVBox = new VBox();
    @FXML
    protected ScrollPane scrollPane = new ScrollPane();

    public void addLogMessage(String message) {
        Platform.runLater(() -> {
            Label messageLabel = new Label(message);
            messagesVBox.getChildren().add(messageLabel);
            scrollPane.setVvalue(1.0);
        });
    }
}
