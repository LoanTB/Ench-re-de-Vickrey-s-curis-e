package com.projetenchere.Seller.View.graphicalUserInterface;

import com.projetenchere.Seller.View.ISellerUserInterface;

public class SellerUserInterfaceFactory implements ISellerUserInterfaceFactory {

    @Override
    public ISellerUserInterface createSellerUserInterface() {
        return new SellerGraphicalUserInterface();
    }
}
