package com.projetenchere.Seller.View;

import com.projetenchere.common.Models.Bid;
import com.projetenchere.common.View.I_UserInterface;

public interface ISellerUserInterface extends I_UserInterface {
    void displayHello();

    void tellSignatureConfigSetup();

    void tellSignatureConfigGeneration();

    void tellSignatureConfigReady();

    void displayEncryptedOffersSet();

    String askBidName();

    String askBidDescription();

    Bid askBid();

    void displayBidCreated(Bid bid);

    void tellSendBidToManager();

    void tellWaitForParticipation();
    void tellNewParticipant();
    void tellParticipationRejected();
    void tellEndOfParticipation();
    void tellSendBiddersVerification();
    void tellSendResolutionToManager();
    void tellFalsifiedSignatureManager();
    void tellFalsifiedSignatureBidder();
    void tellWinnerBid(double prix);
    void tellResultsSend();
    void tellWaitWinnerManifestation();
    void displayEndBid(String idBid);

}
