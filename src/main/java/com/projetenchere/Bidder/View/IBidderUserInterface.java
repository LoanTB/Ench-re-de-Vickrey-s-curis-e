package com.projetenchere.Bidder.View;

import com.projetenchere.Bidder.Model.Bidder;
import com.projetenchere.common.Models.Bid;
import com.projetenchere.common.Models.Offer;

public interface IBidderUserInterface {

    void displayBid(Bid bid);

    Offer readOffer(Bidder bidder);

    void tellOfferWon(double priceToPay);

    void tellOfferLost();

    String readName();

    int readPort();

    void tellOfferSent();

    void tellWaitOfferResult();

}
