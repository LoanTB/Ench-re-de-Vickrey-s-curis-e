package com.projetenchere.Seller.View;

import com.projetenchere.common.Models.Bid;
import com.projetenchere.common.View.IUserInterface;

public interface ISellerUserInterface extends IUserInterface {
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

}
