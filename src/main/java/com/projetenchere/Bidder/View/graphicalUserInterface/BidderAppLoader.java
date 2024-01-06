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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BidderGraphicalUserInterface.fxml"));

        loader.setController(guiInterface);

        Parent root = loader.load();

        primaryStage.setTitle("Bidder");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
