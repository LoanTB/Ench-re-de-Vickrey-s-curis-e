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

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SellerGraphicalUserInterface.fxml"));
        Parent root = loader.load();

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Seller");
        primaryStage.show();

        SellerGraphicalUserInterface guiInterface = loader.getController();
        controllerInstance = new SellerController(guiInterface);

        // Exécutez les opérations initiales sur le thread de l'UI
        controllerInstance.displayHello();
        controllerInstance.setSignatureConfig();

        // Exécutez les opérations bloquantes dans un nouveau thread
        new Thread(() -> {
            controllerInstance.createMyBid();
            controllerInstance.sendMyBid();
            controllerInstance.receiveOffersUntilBidEndAndSendResults();
            try {
                controllerInstance.sendEncryptedOffersSet();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            Platform.runLater(() -> {
                // Assurez-vous que les interactions avec l'interface utilisateur soient sur le thread de l'UI
                controllerInstance.displayWinner();
            });
        }).start();
    }
    public static SellerController getControllerInstance() {
        return controllerInstance;
    }

    public static void launchApp() {
        launch(com.projetenchere.Seller.View.graphicalUserInterface.SellerAppLoader.class);
    }
}