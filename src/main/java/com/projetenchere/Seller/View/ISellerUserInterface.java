package com.projetenchere.Seller.View;

import com.projetenchere.common.Model.Encrypted.EncryptedOffer;
import com.projetenchere.common.Model.Offer;

public interface ISellerUserInterface {

    void diplayHello();
    void displayWinner(String winnerID, Double price);
    void displayOfferReceived(EncryptedOffer encryptedOffer);
}
