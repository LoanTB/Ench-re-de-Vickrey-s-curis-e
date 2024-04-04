package com.projetenchere.manager.loader;

import com.projetenchere.manager.controller.ManagerController;
import com.projetenchere.manager.view.graphicalUserInterface.ManagerGraphicalUserInterface;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ManagerGraphicalApp extends Application {

    private static ManagerController controllerInstance;
    public static ManagerController getControllerInstance() {
        return controllerInstance;
    }
    public static void setControllerInstance(ManagerController managerController) {
        controllerInstance = managerController;
    }

    private ManagerMain main;

    public static void launchApp() {
        launch(ManagerGraphicalApp.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ManagerGraphicalUserInterface.fxml"));
        Parent root = loader.load();
        ManagerMain.setViewInstance(loader.getController());
        ((ManagerGraphicalUserInterface) ManagerMain.getViewInstance()).setPrimaryStage(primaryStage);

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("SecureWin manager");
        primaryStage.show();

        main = new ManagerMain();
        Platform.runLater(() -> {
            main.start();
        });
    }

    @Override
    public void stop(){
        main.interrupt();
    }
}