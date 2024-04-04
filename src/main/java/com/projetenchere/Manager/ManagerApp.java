package com.projetenchere.Manager;

import com.projetenchere.Manager.Loader.ManagerCommandLineApp;
import com.projetenchere.Manager.Loader.ManagerGraphicalApp;

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