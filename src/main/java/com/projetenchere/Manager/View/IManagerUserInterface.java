package com.projetenchere.Manager.View;

import com.projetenchere.Manager.View.commandLineInterface.ManagerCommandLineInterface;
import com.projetenchere.common.Model.Bid;
import com.projetenchere.common.Model.Offer;

import java.util.List;

public interface IManagerUserInterface {

    public Bid askBidInformations();
    public String askBidName();
    public String askBidDescription();
    public void displayPrices(List<Double> DecryptedPrice);
    public void displayWinnerPrice();


}
