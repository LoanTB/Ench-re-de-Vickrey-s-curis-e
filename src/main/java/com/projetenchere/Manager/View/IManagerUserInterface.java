package com.projetenchere.Manager.View;

import com.projetenchere.common.Models.Bid;
import com.projetenchere.common.Models.Bid;
import com.projetenchere.common.View.IUserInterface;

public interface IManagerUserInterface extends IUserInterface {
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

    void tellBidRequest();

    void displayNewBid(Bid bid);
}
