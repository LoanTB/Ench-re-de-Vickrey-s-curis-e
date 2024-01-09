package com.projetenchere.Manager.View.graphicalUserInterface;

import com.projetenchere.Manager.Controllers.ManagerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ManagerAppLoader extends Application {
    private static ManagerController controllerInstance;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ManagerGraphicalUserInterface.fxml"));
        Parent root = loader.load();

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Manager");
        primaryStage.show();

        ManagerGraphicalUserInterface guiInterface = loader.getController();
        controllerInstance = new ManagerController(guiInterface); // Passez l'instance ici

        // Appels de méthode sur controllerInstance
        controllerInstance.displayHello();
        controllerInstance.setSignatureConfig();
        controllerInstance.init();
    }

    public static ManagerController getControllerInstance() {
        return controllerInstance;
    }

    public static void launchApp() {
        launch(ManagerAppLoader.class);
    }
}







