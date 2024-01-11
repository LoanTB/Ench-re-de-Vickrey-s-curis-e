package com.projetenchere.Bidder.View.graphicalUserInterface;

import com.projetenchere.Bidder.Controllers.BidderController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BidderAppLoader extends Application {
    private static BidderController controllerInstance;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BidderGraphicalUserInterface.fxml"));
        Parent root = loader.load();
        BidderGraphicalUserInterface.setInstance(loader.getController());

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Bidder");
        primaryStage.show();

        Platform.runLater(() -> {
            controllerInstance = new BidderController((BidderGraphicalUserInterface) BidderGraphicalUserInterface.getInstance());

            controllerInstance.displayHello();
            try {
                controllerInstance.setSignatureConfig();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            new Thread(() -> {
                controllerInstance.initWithManager();
                controllerInstance.showBids();
                try {
                    controllerInstance.readAndSendOffer();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        });
    }

    public static BidderController getControllerInstance() {
        return controllerInstance;
    }

    public static void launchApp() {
        launch(BidderAppLoader.class);
    }
}







