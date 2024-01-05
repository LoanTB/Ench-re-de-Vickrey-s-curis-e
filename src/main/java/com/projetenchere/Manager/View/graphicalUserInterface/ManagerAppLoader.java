package com.projetenchere.Manager.View.graphicalUserInterface;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ManagerAppLoader extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        IManagerUserInterfaceFactory uiFactory = new ManagerUserInterfaceFactory();
        ManagerGraphicalUserInterface guiInterface = (ManagerGraphicalUserInterface) uiFactory.createManagerUserInterface();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ManagerGraphicalUserInterface.fxml"));

        loader.setController(guiInterface);

        Parent root = loader.load();

        primaryStage.setTitle("Manager");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
