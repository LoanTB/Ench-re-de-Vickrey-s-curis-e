package com.projetenchere.seller.loader;

import com.projetenchere.seller.controller.SellerController;
import com.projetenchere.seller.view.graphicalUserInterface.SellerGraphicalUserInterface;
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

    private SellerMain main;

    public static void launchApp() {
        launch(SellerGraphicalApp.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SellerGraphicalUserInterface.fxml"));
        Parent root = loader.load();
        SellerMain.setViewInstance(loader.getController());
        ((SellerGraphicalUserInterface) SellerMain.getViewInstance()).setPrimaryStage(primaryStage);

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("SecureWin seller");
        primaryStage.show();

        main = new SellerMain();
        Platform.runLater(() -> {
            main.start();
        });
    }

    @Override
    public void stop(){
        main.interrupt();
    }
}