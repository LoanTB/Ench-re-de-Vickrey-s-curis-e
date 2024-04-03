package com.projetenchere.Manager.View;

import com.projetenchere.common.Models.Bid;
import com.projetenchere.common.View.IUserInterface;

public interface IManagerUserInterface extends IUserInterface {
    void displayHello();

    void diplayEndBid(String idBid);

    void tellSignatureConfigSetup();

    void tellSignatureConfigGeneration();

    void tellSignatureConfigReady();

    void tellManagerReadyToProcessBids();

    void tellBidRequest();

    void displayNewBid(Bid bid);
}
