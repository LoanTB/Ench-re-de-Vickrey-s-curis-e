package com.projetenchere.Manager.Loader;

import com.projetenchere.Manager.View.commandLineInterface.ManagerCommandLineInterface;

public class ManagerCommandLineApp {
    public static void launchApp() throws InterruptedException {
        ManagerMain.setViewInstance(new ManagerCommandLineInterface());
        ManagerMain main = new ManagerMain();
        main.start();
        main.join();
    }

}







