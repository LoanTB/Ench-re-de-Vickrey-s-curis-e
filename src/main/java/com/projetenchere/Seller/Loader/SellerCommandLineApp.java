package com.projetenchere.Seller.Loader;

import com.projetenchere.Seller.View.commandLineInterface.SellerCommandLineInterface;

public class SellerCommandLineApp{
    public static void launchApp() throws Exception {
        SellerMain.setViewInstance(new SellerCommandLineInterface());
        SellerMain main = new SellerMain();
        main.start();
        main.join();
    }
}