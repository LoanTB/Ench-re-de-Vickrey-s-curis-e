package com.projetenchere.Bidder.View.graphicalUserInterface;

import com.projetenchere.Bidder.Model.Bidder;
import com.projetenchere.Bidder.View.IBidderUserInterface;
import com.projetenchere.common.Models.CurrentBids;
import com.projetenchere.common.Models.Offer;

public class BidderGraphicalUserInterface implements IBidderUserInterface {
    @Override
    public void displayBid(CurrentBids currentBids) {

    }

    @Override
    public Offer readOffer(Bidder bidder, CurrentBids currentBids) {
        return null;
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
    public void tellOfferWon(double priceToPay) {

    }

    @Override
    public void tellOfferLost() {

    }

    @Override
    public void tellOfferSent() {

    }

    @Override
    public void tellWaitOfferResult() {

    }

    @Override
    public void tellWaitBidsAnnoncement() {

    }

    @Override
    public void tellWaitBidsPublicKeysAnnoncement() {

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
    public void displayHello() {

    }

    @Override
    public void tellReceivingInformationOf(String id, String type) {

    }

    @Override
    public void tellReceiptOfCurrentBids() {

    }

    @Override
    public void tellReceiptOfEncryptionKeysForCurrentBids() {

    }

    @Override
    public void tellReceiptOfBidResult(String id) {

    }

    @Override
    public void tellSendRequestOffers() {

    }
}
