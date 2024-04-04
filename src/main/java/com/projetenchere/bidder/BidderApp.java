package com.projetenchere.bidder;

import com.projetenchere.bidder.loader.BidderCommandLineApp;
import com.projetenchere.bidder.loader.BidderGraphicalApp;

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
