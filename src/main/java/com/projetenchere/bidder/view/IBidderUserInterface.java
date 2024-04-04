package com.projetenchere.bidder.view;

import com.projetenchere.bidder.model.Bidder;
import com.projetenchere.common.model.CurrentBids;
import com.projetenchere.common.model.Offer;
import com.projetenchere.common.view.IUserInterface;

public interface IBidderUserInterface extends IUserInterface {
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
