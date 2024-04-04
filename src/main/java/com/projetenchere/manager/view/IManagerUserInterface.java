package com.projetenchere.manager.view;

import com.projetenchere.common.model.Bid;
import com.projetenchere.common.view.IUserInterface;

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
