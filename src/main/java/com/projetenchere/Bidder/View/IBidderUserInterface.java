package com.projetenchere.Bidder.View;
import com.projetenchere.common.Model.Bid;
import com.projetenchere.common.Model.Offer;


public interface IBidderUserInterface {

    void displayBid(Bid bid);

    Offer readOffer();

    void tellOfferWon(int priceToPay);

    void tellOfferLost();



}
