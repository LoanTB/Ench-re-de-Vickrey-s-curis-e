package com.projetenchere.Bidder;

import com.projetenchere.Bidder.Loader.BidderCommandLineApp;
import com.projetenchere.Bidder.Loader.BidderGraphicalApp;
import com.projetenchere.Bidder.View.IBidderUserInterface;

public class BidderApp {

    private static IBidderUserInterface viewInstance = null;

    public static IBidderUserInterface getViewInstance() {
        if (viewInstance == null) {
            throw new NullPointerException("Instance non initialisée");
        }
        return viewInstance;
    }

    public static void setViewInstance(IBidderUserInterface instance) {
        viewInstance = instance;
    }
    public static void main(String[] args) {
        BidderGraphicalApp.launchApp();
    }
}
