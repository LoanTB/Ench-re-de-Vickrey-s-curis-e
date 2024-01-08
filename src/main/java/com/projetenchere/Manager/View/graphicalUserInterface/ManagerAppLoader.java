package com.projetenchere.Manager.View.graphicalUserInterface;

import com.projetenchere.Manager.Controllers.ManagerController;
import com.projetenchere.Manager.View.IManagerUserInterface;
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

        IManagerUserInterfaceFactory uiFactory = new ManagerUserInterfaceFactory();

        IManagerUserInterface guiInterface = uiFactory.createManagerUserInterface();

        loader.setController((ManagerGraphicalUserInterface) guiInterface);

        Parent root = loader.load();

        Scene scene = new Scene(root);
        String css = this.getClass().getResource("/css/style.css").toExternalForm();
        scene.getStylesheets().add(css);

        primaryStage.setTitle("Manager");
        primaryStage.setScene(scene);
        primaryStage.show();

        this.controller = new ManagerController(uiFactory);

        this.controller.displayHello();
        this.controller.setSignatureConfig();
        this.controller.init();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

