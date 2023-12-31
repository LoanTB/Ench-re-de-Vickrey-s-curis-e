package com.projetenchere.Manager.View.graphicalUserInterface;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ManagerAppLoader extends Application {

    private static ManagerGraphicalUserInterface guiInterface;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ManagerGraphicalUserInterface.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Manager Application");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void launchApp(ManagerGraphicalUserInterface gui) {
        guiInterface = gui;
        launch();
    }
}
