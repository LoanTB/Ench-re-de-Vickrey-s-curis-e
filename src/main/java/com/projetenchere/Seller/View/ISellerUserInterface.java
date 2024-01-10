package com.projetenchere.Seller.View;

import com.projetenchere.common.Models.Bid;
import com.projetenchere.common.View.I_UserInterface;

import java.time.LocalDateTime;

public interface ISellerUserInterface extends I_UserInterface {
    void displayHello();

    void tellSignatureConfigSetup(); //TODO : Prochain sprint : Voir pour la dupp de code pour les tellSignatureConfig et autres de chaques utilisateurs
    void tellSignatureConfigGeneration();
    void tellSignatureConfigReady();

    void displayWinner(String winnerID, Double price);
    void displayOfferReceived();
    void displayEncryptedOffersSetent();
    void displayResultsSent();
    void displayBidReceived(String bid);
    void waitOffers();
    String askBidName();
    String askBidDescription();

    Bid askBid();
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
    void tellSendBidToManager();
    void tellManagerConfirmsReceipt();
}
