package com.projetenchere.Bidder.Loader;

import com.projetenchere.Bidder.Controllers.BidderController;
import com.projetenchere.Bidder.View.graphicalUserInterface.BidderGraphicalUserInterface;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BidderGraphicalApp extends Application {
    private static BidderController controllerInstance;
    public static BidderController getControllerInstance() {
        if (controllerInstance == null) {throw new NullPointerException("Instance controller non initialisée");}
        return controllerInstance;
    }
    public static void setControllerInstance(BidderController bidderController) {
        controllerInstance = bidderController;
    }

    private BidderMain main;

    public static void launchApp() {
        launch(BidderGraphicalApp.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BidderGraphicalUserInterface.fxml"));
        Parent root = loader.load();
        BidderMain.setViewInstance(loader.getController());
        ((BidderGraphicalUserInterface) BidderMain.getViewInstance()).setPrimaryStage(primaryStage);

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("SecureWin Bidder");
        primaryStage.show();

        main = new BidderMain();
        main.start();
    }

    @Override
    public void stop(){
        main.interrupt();
    }
}







