package com.projetenchere.Bidder.View;

import com.projetenchere.Bidder.Model.Bidder;
import com.projetenchere.common.Models.CurrentBids;
import com.projetenchere.common.Models.Offer;
import com.projetenchere.common.View.I_UserInterface;

public interface IBidderUserInterface extends I_UserInterface {
    void displayBid(CurrentBids currentBids);
    Offer readOffer(Bidder bidder, CurrentBids currentBids);
    void tellSignatureConfigSetup(); //TODO : Prochain sprint : Voir pour la dupp de code pour les tellSignatureConfig et autres de chaques utilisateurs
    void tellSignatureConfigGeneration();
    void tellSignatureConfigReady();
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
