package com.projetenchere.Bidder;

import com.projetenchere.Bidder.Loader.BidderCommandLineApp;
import com.projetenchere.Bidder.Loader.BidderGraphicalApp;
import com.projetenchere.Manager.Loader.ManagerCommandLineApp;
import com.projetenchere.Manager.Loader.ManagerGraphicalApp;

import java.util.Arrays;

public class BidderApp {
    public static void main(String[] args) throws InterruptedException {
        if (Arrays.asList(args).contains("-g") || Arrays.asList(args).contains("--gui")){
            BidderGraphicalApp.launchApp();
        } else {
            BidderCommandLineApp.launchApp();
        }
    }
}
