package com.projetenchere.Bidder.View;

import com.projetenchere.Bidder.Model.Bidder;
import com.projetenchere.common.Models.CurrentBids;
import com.projetenchere.common.Models.Offer;
import com.projetenchere.common.View.I_UserInterface;

public interface IBidderUserInterface extends I_UserInterface {
    void displayBid(CurrentBids currentBids);

    Offer readOffer(Bidder bidder, CurrentBids currentBids);

    void tellSignatureConfigSetup();

    void tellSignatureConfigGeneration();

    void tellSignatureConfigReady();

    void tellOfferWon(double priceToPay);

    void tellOfferLost();

    void tellOfferSent();

    void tellWaitManagerSecurityInformations();

    void displayHello();

    void tellReceiptOfCurrentBids();

    void tellFalsifiedSignatureManager();
    void tellFalsifiedSignatureSeller();

}
