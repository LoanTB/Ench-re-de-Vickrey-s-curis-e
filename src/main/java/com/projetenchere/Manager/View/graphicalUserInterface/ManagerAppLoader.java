package com.projetenchere.Manager.View.graphicalUserInterface;

import com.projetenchere.Manager.Controllers.ManagerController;
import com.projetenchere.Manager.View.IManagerUserInterface;
import com.projetenchere.Manager.View.IManagerUserInterfaceFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ManagerAppLoader extends Application {

    private ManagerController controller;
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ManagerGraphicalUserInterface.fxml"));

        ManagerGraphicalUserInterface guiInterface = new ManagerGraphicalUserInterface();
        loader.setController(guiInterface);
        Parent root = loader.load();

        Scene scene = new Scene(root);
        if (this.getClass().getResource("/css/style.css") != null) {
            String css = this.getClass().getResource("/css/style.css").toExternalForm();
            scene.getStylesheets().add(css);
        }

        primaryStage.setTitle("Manager");
        primaryStage.setScene(scene);
        primaryStage.show();

        guiInterface.displayHello();
    }

    public static void launchApp() {
        launch(ManagerAppLoader.class);
    }
}


