package com.projetenchere.Manager.View;

import com.projetenchere.common.Model.Bid;

import java.time.LocalDateTime;
import java.util.List;

public interface IManagerUserInterface {

    public Bid askBidInformations();
    public String askBidName();
    public String askBidDescription();
    public LocalDateTime askBidEndTime();
    public String askSellerAdress();
    public void displayPrices(List<Double> DecryptedPrice);
    public void displayWinnerPrice();


}
