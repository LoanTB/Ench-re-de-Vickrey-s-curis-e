package com.projetenchere.Manager.View;

import com.projetenchere.common.Models.Winner;

import java.time.LocalDateTime;

public interface IManagerUserInterface {
    void displayHello();
    void displayGenerateKey();
    void displayBidLaunch();
    void displayReceivedPrices();
    void displayPriceProcessing();
    void displaySentWinnerPrice();
    void displayEndOfAuction();
    String askBidName();
    String askBidDescription();
    LocalDateTime askBidEndTime();
    String askSellerAddress();
    void displayWinnerPrice(Winner winner);
    void tellConnectingNewSeller(String id);
    void tellConnectingNewBidder(String id);
    void tellRequestInformationAboutBiddersBySeller(String id);
    void tellRequestCurrentBidsByBidder(String id);
    void tellRequestToDetermineTheWinnerOfBidBySeller(String idBid,String idSeller);
    void tellManagerReadyToProcessBids();
    void tellBidReceivedby(String idSeller, String idBid);
    void tellReceivingAndReplyToInformationOf(String id, String type);
}
