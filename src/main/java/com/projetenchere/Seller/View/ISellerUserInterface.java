package com.projetenchere.Seller.View;

import com.projetenchere.common.Models.Bid;
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
    String askBidName();
    String askBidDescription();
    LocalDateTime askBidEndTime();
    String readName();
    String readSurname();
    int readPort();
    void tellWaitManager();
    void tellManagerFound();
    void tellWaitManagerSecurityInformations();
    void tellWaitWinnerDeclaration();
    void displayBidCreated(Bid bid);
    void tellSuccessfulSecuringOfTheCommunicationChannelWithTheManager();
    void tellReceivingInformationOf(String id, String type);
    void tellReceiptOfferByBidder(String id);
    void tellReceiptBidResult(String id);
}
