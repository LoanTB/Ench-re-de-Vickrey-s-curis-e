package com.projetenchere.Seller.Loader;

import com.projetenchere.Bidder.BidderApp;
import com.projetenchere.Bidder.View.graphicalUserInterface.BidderGraphicalUserInterface;
import com.projetenchere.Manager.ManagerApp;
import com.projetenchere.Manager.View.graphicalUserInterface.ManagerGraphicalUserInterface;
import com.projetenchere.Seller.Controllers.SellerController;
import com.projetenchere.Seller.SellerApp;
import com.projetenchere.Seller.View.graphicalUserInterface.SellerGraphicalUserInterface;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SellerGraphicalApp extends Application {
    private static SellerController controllerInstance;

    public static SellerController getControllerInstance() {
        return controllerInstance;
    }
    public static void setControllerInstance(SellerController sellerController) {
        controllerInstance = sellerController;
    }

    public static void launchApp() {
        launch(SellerGraphicalApp.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SellerGraphicalUserInterface.fxml"));
        Parent root = loader.load();
        SellerApp.setViewInstance(loader.getController());
        ((SellerGraphicalUserInterface) SellerApp.getViewInstance()).setPrimaryStage(primaryStage);

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("SecureWin Seller");
        primaryStage.show();

        (new SellerMain()).start();
    }
}