package com.projetenchere.Manager.View;

import com.projetenchere.common.Model.Bid;

import java.time.LocalDateTime;
import java.util.Set;

public interface IManagerUserInterface {

     Bid askBidInformations();
     String askBidName();
     String askBidDescription();
     LocalDateTime askBidEndTime();
     String askSellerAdress();
     void displayPrices(Set<Double> DecryptedPrice);
     void displayWinnerPrice();


}
