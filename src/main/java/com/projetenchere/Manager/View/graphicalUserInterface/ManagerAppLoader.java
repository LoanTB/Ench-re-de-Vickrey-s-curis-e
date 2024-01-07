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
        // This line creates an instance of the UI interface
        IManagerUserInterface guiInterface = uiFactory.createManagerUserInterface();

        loader.setController((ManagerGraphicalUserInterface) guiInterface);

        Parent root = loader.load();

        primaryStage.setTitle("Manager");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        // Here you should pass the factory, not the interface
        this.controller = new ManagerController(uiFactory);

        // Assuming displayHello is now handled by the controller
        this.controller.displayHello();
        this.controller.setSignatureConfig();
        this.controller.init();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

