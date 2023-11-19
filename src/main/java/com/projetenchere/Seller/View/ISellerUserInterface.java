package com.projetenchere.Seller.View;

import com.projetenchere.common.Models.Encrypted.EncryptedOffer;

import java.time.LocalDateTime;

public interface ISellerUserInterface {
    void displayHello();
    void displayWinner(String winnerID, Double price);
    void displayOfferReceived(EncryptedOffer encryptedOffer);
    void displayEncryptedPriceSended();
    void displayResultsSent();
    void displayBidReceived(String bid);
    void waitOffers();
    int askBidId();
    String askBidName();
    String askBidDescription();
    LocalDateTime askBidEndTime();
    String askSellerAddress();
    int askSellerPort();
}
