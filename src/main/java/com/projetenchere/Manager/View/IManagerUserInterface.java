package com.projetenchere.Manager.View;

import com.projetenchere.common.Models.Bid;
import com.projetenchere.common.View.I_UserInterface;

public interface IManagerUserInterface extends I_UserInterface {
    void displayHello();

    void tellKeysGeneration();

    void tellKeysReady();

    void tellSignatureConfigSetup();

    void tellSignatureConfigGeneration();

    void tellSignatureConfigReady();

    void tellManagerReadyToProcessBids();

    void tellFalsifiedSignatureBidder();
    void tellFalsifiedSignatureSeller();

    void displayBidderAskBids();
    void displaySendBidderPubKey();
    void diplayEndBid(String idBid);
    void displayNewBid(Bid bid);

}
