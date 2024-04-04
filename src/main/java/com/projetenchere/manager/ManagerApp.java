package com.projetenchere.manager;

import com.projetenchere.manager.loader.ManagerCommandLineApp;
import com.projetenchere.manager.loader.ManagerGraphicalApp;

import java.util.Arrays;

public class ManagerApp {
    public static void main(String[] args) throws InterruptedException {
        if (Arrays.asList(args).contains("-g") || Arrays.asList(args).contains("--gui")){
            ManagerGraphicalApp.launchApp();
        } else {
            ManagerCommandLineApp.launchApp();
        }
    }
}