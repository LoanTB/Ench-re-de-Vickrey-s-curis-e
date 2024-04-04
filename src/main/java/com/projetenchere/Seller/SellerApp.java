package com.projetenchere.Seller;

import com.projetenchere.Bidder.Loader.BidderCommandLineApp;
import com.projetenchere.Bidder.Loader.BidderGraphicalApp;
import com.projetenchere.Seller.Loader.SellerCommandLineApp;
import com.projetenchere.Seller.Loader.SellerGraphicalApp;

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
