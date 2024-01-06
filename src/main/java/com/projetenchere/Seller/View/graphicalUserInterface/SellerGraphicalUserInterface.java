package com.projetenchere.Seller.View.graphicalUserInterface;

import com.projetenchere.Seller.View.ISellerUserInterface;
import com.projetenchere.common.Models.Encrypted.EncryptedOffer;
import com.projetenchere.common.Models.Bid;

import java.time.LocalDateTime;

public class SellerGraphicalUserInterface implements ISellerUserInterface {


    @Override
    public void displayHello() {

    }

    @Override
    public void tellSignatureConfigSetup() {

    }

    @Override
    public void tellSignatureConfigGeneration() {

    }

    @Override
    public void tellSignatureConfigReady() {
    }

    @Override
    public void displayWinner(String winnerID, Double price) {

    }

    @Override
    public void displayOfferReceived() {

    }

    @Override
    public void displayEncryptedOffersSetent() {

    }

    @Override
    public void displayResultsSent() {

    }

    @Override
    public void displayBidReceived(String bid) {

    }

    @Override
    public void waitOffers() {

    }

    @Override
    public String askBidName() {
        return null;
    }

    @Override
    public String askBidDescription() {
        return null;
    }

    @Override
    public LocalDateTime askBidEndTime() {
        return null;
    }

    @Override
    public String readName() {
        return null;
    }

    @Override
    public String readSurname() {
        return null;
    }

    @Override
    public int readPort() {
        return 0;
    }

    @Override
    public void tellWaitManager() {

    }

    @Override
    public void tellManagerFound() {

    }

    @Override
    public void tellWaitManagerSecurityInformations() {

    }

    @Override
    public void tellWaitWinnerDeclaration() {

    }

    @Override
    public void displayBidCreated(Bid bid) {

    }

    @Override
    public void tellSuccessfulSecuringOfTheCommunicationChannelWithTheManager() {

    }

    @Override
    public void tellReceivingInformationOf(String id, String type) {

    }

    @Override
    public void tellReceiptOfferByBidder(String id) {

    }

    @Override
    public void tellReceiptBidResult(String id) {

    }

    @Override
    public void tellSendBidToManager() {

    }

    @Override
    public void tellManagerConfirmsReceipt() {

    }
}
