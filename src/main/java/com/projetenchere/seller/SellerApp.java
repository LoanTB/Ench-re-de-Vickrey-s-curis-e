package com.projetenchere.seller;

import com.projetenchere.seller.loader.SellerCommandLineApp;
import com.projetenchere.seller.loader.SellerGraphicalApp;

import java.util.Arrays;

public class SellerApp {
    public static void main(String[] args) throws Exception {
        if (Arrays.asList(args).contains("-g") || Arrays.asList(args).contains("--gui")){
            SellerGraphicalApp.launchApp();
        } else {
            SellerCommandLineApp.launchApp();
        }
    }
}
