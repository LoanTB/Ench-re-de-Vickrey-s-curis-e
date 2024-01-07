package com.projetenchere.Bidder.View.graphicalUserInterface;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BidderAppLoader extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        IBidderUserInterfaceFactory uiFactory = new BidderUserInterfaceFactory();
        BidderGraphicalUserInterface guiInterface = (BidderGraphicalUserInterface) uiFactory.createBidderUserInterface();

        // Load FXML and apply controler
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BidderGraphicalUserInterface.fxml"));
        loader.setController(guiInterface);
        Parent root = loader.load();

        // Create scene and add CSS
        Scene scene = new Scene(root);
        String css = this.getClass().getResource("/css/style.css").toExternalForm();
        scene.getStylesheets().add(css);

        primaryStage.setTitle("Bidder");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
