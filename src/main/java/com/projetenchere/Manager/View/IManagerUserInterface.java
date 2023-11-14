package com.projetenchere.Manager.View;

import com.projetenchere.common.Models.Bid;
import com.projetenchere.common.Models.Network.Communication.Winner;

import java.time.LocalDateTime;

public interface IManagerUserInterface {

    Bid askBidInformations();

    int askBidId();

    String askBidName();

    String askBidDescription();

    LocalDateTime askBidEndTime();

    String askSellerAddress();

    void displayWinnerPrice(Winner winner);

}
