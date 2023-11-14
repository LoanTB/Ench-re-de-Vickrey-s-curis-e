package com.projetenchere.Seller.View;

import com.projetenchere.common.Models.Encrypted.EncryptedOffer;

public interface ISellerUserInterface {
    void diplayHello();
    void displayWinner(String winnerID, Double price);
    void displayOfferReceived(EncryptedOffer encryptedOffer);
    void displayEncryptedPriceSended();
    void displayResultsSent();
    void displayBidReceived(String bid);
    void waitOffers();
}
