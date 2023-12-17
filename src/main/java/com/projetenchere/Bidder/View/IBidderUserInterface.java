package com.projetenchere.Bidder.View;

import com.projetenchere.Bidder.Model.Bidder;
import com.projetenchere.common.Models.CurrentBids;
import com.projetenchere.common.Models.Offer;

public interface IBidderUserInterface {
    void displayBid(CurrentBids currentBids);
    Offer readOffer(Bidder bidder, CurrentBids currentBids);
    void tellOfferWon(double priceToPay);
    void tellOfferLost();
    void tellOfferSent();
    void tellWaitOfferResult();
    void tellWaitBidsAnnoncement();
    void tellWaitBidsPublicKeysAnnoncement();
    String readName();
    String readSurname();
    int readPort();
    void tellWaitManager();
    void tellManagerFound();
    void tellWaitManagerSecurityInformations();
    void displayHello();
    void tellReceivingInformationOf(String id, String type);
    void tellReceiptOfCurrentBids();
    void tellReceiptOfEncryptionKeysForCurrentBids();
    void tellReceiptOfBidResult(String id);
    void tellSendRequestOffers();
}
