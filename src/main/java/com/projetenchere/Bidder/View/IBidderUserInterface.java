package com.projetenchere.Bidder.View;
import com.projetenchere.common.Model.Offer;


public interface IBidderUserInterface {

    void displayCurrentBid();

    Offer readOffer();

    void tellOfferWon(int priceToPay);

    void tellOfferLost();




}
