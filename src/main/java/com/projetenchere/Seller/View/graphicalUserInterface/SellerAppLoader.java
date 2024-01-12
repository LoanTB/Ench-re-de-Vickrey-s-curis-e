package com.projetenchere.Seller.View.graphicalUserInterface;

import com.projetenchere.Seller.Controllers.SellerController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SellerAppLoader extends Application {
    private static SellerController controllerInstance;

    public static SellerController getControllerInstance() {
        return controllerInstance;
    }

    public static void launchApp() {
        launch(SellerAppLoader.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SellerGraphicalUserInterface.fxml"));
        Parent root = loader.load();
        SellerGraphicalUserInterface.setInstance(loader.getController());
        SellerGraphicalUserInterface.getInstance().setPrimaryStage(primaryStage);

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("SecureWin Seller");
        primaryStage.show();

        controllerInstance = new SellerController((SellerGraphicalUserInterface) SellerGraphicalUserInterface.getInstance());

        controllerInstance.displayHello();
        controllerInstance.setSignatureConfig();

        new Thread(() -> {
            controllerInstance.createMyBid();
            controllerInstance.sendMyBid();
            controllerInstance.receiveOkUntilCheckEndAndSendResults();
            try {
                controllerInstance.sendEncryptedOffersSet();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            Platform.runLater(() -> {
                controllerInstance.displayWinner();
            });
        }).start();
    }
}