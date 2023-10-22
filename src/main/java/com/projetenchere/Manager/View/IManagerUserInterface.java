package com.projetenchere.Manager.View;

import com.projetenchere.common.Model.Bid;
import com.projetenchere.common.Model.Winner;

import java.time.LocalDateTime;
import java.util.Set;

public interface IManagerUserInterface {

    Bid askBidInformations();

    String askBidName();

    String askBidDescription();

    LocalDateTime askBidEndTime();

    String askSellerAddress();

    void displayPrices(Set<Double> DecryptedPrice);

    void displayWinnerPrice(Winner winner);


}
