package com.projetenchere.Manager;

import com.projetenchere.Manager.Loader.ManagerCommandLineApp;
import com.projetenchere.Manager.Loader.ManagerGraphicalApp;
import com.projetenchere.Manager.View.IManagerUserInterface;

public class ManagerApp {

    private static IManagerUserInterface viewInstance = null;

    public static IManagerUserInterface getViewInstance() {
        if (viewInstance == null) {
            throw new NullPointerException("Instance non initialisée");
        }
        return viewInstance;
    }

    public static void setViewInstance(IManagerUserInterface instance) {
        viewInstance = instance;
    }

    public static void main(String[] args) {
        ManagerCommandLineApp.launchApp();
    }
}