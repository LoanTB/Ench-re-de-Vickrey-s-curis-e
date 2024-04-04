package com.projetenchere.manager.loader;

import com.projetenchere.manager.view.commandLineInterface.ManagerCommandLineInterface;

public class ManagerCommandLineApp {
    public static void launchApp() throws InterruptedException {
        ManagerMain.setViewInstance(new ManagerCommandLineInterface());
        ManagerMain main = new ManagerMain();
        main.start();
        main.join();
    }

}







