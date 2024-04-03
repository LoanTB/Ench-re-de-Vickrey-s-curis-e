package com.projetenchere.Seller.Loader;

import com.projetenchere.Seller.SellerApp;
import com.projetenchere.Seller.View.commandLineInterface.SellerCommandLineInterface;

public class SellerCommandLineApp{
    public static void launchApp() throws Exception {
        SellerApp.setViewInstance(new SellerCommandLineInterface());
        (new SellerMain()).start();
    }
}