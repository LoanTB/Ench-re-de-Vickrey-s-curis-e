package com.projetenchere.Seller.Loader;

import com.projetenchere.Bidder.BidderApp;
import com.projetenchere.Bidder.View.commandLineInterface.BidderCommandLineInterface;
import com.projetenchere.Seller.Controllers.SellerController;
import com.projetenchere.Seller.SellerApp;
import com.projetenchere.Seller.View.ISellerUserInterface;
import com.projetenchere.Seller.View.commandLineInterface.SellerCommandLineInterface;
import javafx.application.Platform;

public class SellerCommandLineApp{

    public static void launchApp() throws Exception {
        SellerApp.setViewInstance(new SellerCommandLineInterface());
        (new SellerMain()).start();
    }
}