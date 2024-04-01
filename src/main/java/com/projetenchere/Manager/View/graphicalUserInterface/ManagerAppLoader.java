package com.projetenchere.Manager.View.graphicalUserInterface;

import com.projetenchere.Manager.Controllers.ManagerController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.security.SignatureException;

public class ManagerAppLoader extends Application {
    private static ManagerController controllerInstance;

    public static void launchApp() {
        launch(ManagerAppLoader.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ManagerGraphicalUserInterface.fxml"));
        Parent root = loader.load();
        ManagerGraphicalUserInterface.setInstance(loader.getController());

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("SecureWin Manager");
        primaryStage.show();

        Platform.runLater(() -> {
            controllerInstance = new ManagerController((ManagerGraphicalUserInterface) ManagerGraphicalUserInterface.getInstance());
            controllerInstance.displayHello();
            try {
                controllerInstance.setSignatureConfig();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            try {
                controllerInstance.init();
            } catch (SignatureException e) {
                throw new RuntimeException(e);
            }
        });
    }
}







