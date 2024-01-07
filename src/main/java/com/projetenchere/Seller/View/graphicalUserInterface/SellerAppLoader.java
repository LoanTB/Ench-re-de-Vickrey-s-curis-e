package com.projetenchere.Seller.View.graphicalUserInterface;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.projetenchere.Seller.Controllers.SellerController;

public class SellerAppLoader extends Application {

    private SellerController controller;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SellerGraphicalUserInterface.fxml"));

        ISellerUserInterfaceFactory uiFactory = new SellerUserInterfaceFactory();

        SellerGraphicalUserInterface guiInterface = (SellerGraphicalUserInterface) uiFactory.createSellerUserInterface();
        loader.setController(guiInterface);

        Parent root = loader.load();

        Scene scene = new Scene(root);
        String css = this.getClass().getResource("/css/style.css").toExternalForm();
        scene.getStylesheets().add(css);

        primaryStage.setTitle("Seller");
        primaryStage.setScene(scene);
        primaryStage.show();

        this.controller = new SellerController(uiFactory);

        this.controller.displayHello();
        this.controller.setSignatureConfig();
        this.controller.createMyBid();
        this.controller.sendMyBid();
        this.controller.receiveOffersUntilBidEndAndSendResults();
        this.controller.sendEncryptedOffersSet();
        this.controller.displayWinner();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
