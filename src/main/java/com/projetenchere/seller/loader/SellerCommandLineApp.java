package com.projetenchere.seller.loader;

import com.projetenchere.seller.view.commandLineInterface.SellerCommandLineInterface;

public class SellerCommandLineApp{
    public static void launchApp() throws Exception {
        SellerMain.setViewInstance(new SellerCommandLineInterface());
        SellerMain main = new SellerMain();
        main.start();
        main.join();
    }
}