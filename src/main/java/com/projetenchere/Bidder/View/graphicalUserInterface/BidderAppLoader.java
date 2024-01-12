package com.projetenchere.Bidder.View.graphicalUserInterface;

import com.projetenchere.Bidder.Controllers.BidderController;
import com.projetenchere.Seller.View.graphicalUserInterface.SellerGraphicalUserInterface;
import javafx.application.Application;
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
        SellerGraphicalUserInterface.getInstance().setPrimaryStage(primaryStage);

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("SecureWin Bidder");
        primaryStage.show();

        new Thread(() -> {
            controllerInstance = new BidderController((BidderGraphicalUserInterface) BidderGraphicalUserInterface.getInstance());
            controllerInstance.displayHello();
            try {
                controllerInstance.setSignatureConfig();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            controllerInstance.initWithManager();
            try {
                controllerInstance.readAndSendOffer();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public static BidderController getControllerInstance() {
        return controllerInstance;
    }

    public static void launchApp() {
        launch(BidderAppLoader.class);
    }
}







