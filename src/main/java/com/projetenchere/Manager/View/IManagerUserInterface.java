package com.projetenchere.Manager.View;

import com.projetenchere.common.Model.Bid;
import com.projetenchere.common.Model.Winner;

import java.time.LocalDateTime;
import java.util.List;

public interface IManagerUserInterface {

    Bid askBidInformations();

    String askBidName();

    String askBidDescription();

    LocalDateTime askBidEndTime();

    String askSellerAddress();

    void displayWinnerPrice(Winner winner);
}
