package com.projetenchere.Seller;

import com.projetenchere.Seller.Controller.SellerController;
import com.projetenchere.Seller.Controller.SellerNetworkController;

import java.net.UnknownHostException;

public class SellerApp {
    public static void main(String[] args) throws UnknownHostException {
        SellerController controller = new SellerController();
        SellerNetworkController networkController = new SellerNetworkController();

        controller.diplayHello();

    }
}

