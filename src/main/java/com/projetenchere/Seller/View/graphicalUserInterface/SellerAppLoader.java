package com.projetenchere.Seller.View.graphicalUserInterface;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SellerAppLoader extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ISellerUserInterfaceFactory uiFactory = new SellerUserInterfaceFactory();
        SellerGraphicalUserInterface guiInterface = (SellerGraphicalUserInterface) uiFactory.createSellerUserInterface();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SellerGraphicalUserInterface.fxml"));

        loader.setController(guiInterface);

        Parent root = loader.load();

        primaryStage.setTitle("Seller");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
