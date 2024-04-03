package com.projetenchere.Seller;

import com.projetenchere.Bidder.BidderApp;
import com.projetenchere.Bidder.View.IBidderUserInterface;
import com.projetenchere.Seller.Loader.SellerCommandLineApp;
import com.projetenchere.Seller.Loader.SellerGraphicalApp;
import com.projetenchere.Seller.View.ISellerUserInterface;

public class SellerApp {

    private static ISellerUserInterface viewInstance = null;

    public static ISellerUserInterface getViewInstance() {
        if (viewInstance == null) {
            throw new NullPointerException("Instance non initialisée");
        }
        return viewInstance;
    }

    public static void setViewInstance(ISellerUserInterface instance) {
        viewInstance = instance;
    }

    public static void main(String[] args) throws Exception {
        SellerCommandLineApp.launchApp();
    }
}
