package com.projetenchere.Manager.View.graphicalUserInterface;

import com.projetenchere.Manager.View.IManagerUserInterface;
import com.projetenchere.Manager.View.IManagerUserInterfaceFactory;
import javafx.application.Application;

public class ManagerUserInterfaceFactory implements IManagerUserInterfaceFactory {

    @Override
    public IManagerUserInterface createManagerUserInterface() {
        Thread javaFxThread = new Thread(() -> {
            Application.launch(ManagerAppLoader.class);
        });

        javaFxThread.start();
        return new ManagerGraphicalUserInterface();
    }
}

