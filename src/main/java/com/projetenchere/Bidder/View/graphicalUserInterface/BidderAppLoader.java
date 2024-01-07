package com.projetenchere.Bidder.View.graphicalUserInterface;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.projetenchere.Bidder.Controllers.BidderController;

public class BidderAppLoader extends Application {

    private BidderController controller;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BidderGraphicalUserInterface.fxml"));

        IBidderUserInterfaceFactory uiFactory = new BidderUserInterfaceFactory();
        BidderGraphicalUserInterface guiInterface = (BidderGraphicalUserInterface) uiFactory.createBidderUserInterface();
        loader.setController(guiInterface);

        Parent root = loader.load();

        Scene scene = new Scene(root);
        String css = this.getClass().getResource("/css/style.css").toExternalForm();
        scene.getStylesheets().add(css);

        primaryStage.setTitle("Bidder");
        primaryStage.setScene(scene);
        primaryStage.show();

        this.controller = new BidderController(uiFactory);

        this.controller.displayHello();
        this.controller.setSignatureConfig();
        this.controller.initWithManager();
        this.controller.showBids();
        this.controller.readAndSendOffer();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
