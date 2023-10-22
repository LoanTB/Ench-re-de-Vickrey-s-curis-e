package com.projetenchere.Seller.Controller;

import com.projetenchere.Seller.Model.Seller;
import com.projetenchere.Seller.View.ISellerUserInterface;
import com.projetenchere.Seller.View.commandLineInterface.SellerCommandLineInterface;
import com.projetenchere.common.Model.Bid;
import com.projetenchere.common.Model.Encrypted.EncryptedOffer;
import com.projetenchere.common.Model.Encrypted.EncryptedPrices;
import com.projetenchere.common.Model.Sendable.ObjectSender;
import com.projetenchere.common.Model.Winner;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.*;

public class SellerController {

    private static final ISellerUserInterface ui = new SellerCommandLineInterface();
    private Bid currentBid;
    private final Seller seller = new Seller();

    private Winner winner = null;
    SellerNetworkController networkController = new SellerNetworkController();

    public SellerController() throws UnknownHostException {}

    public Bid getCurrentBid() {
        return currentBid;
    }

    public void fetchCurrentBid(){
        this.currentBid = networkController.fetchBid();
        ui.displayBidReceived();
    }

    public boolean auctionInProgress(){
        return (!this.currentBid.isOver());
    }

    public void receiveOffersUntilBidEnd(){
        EncryptedOffer offerReceived;
        while (auctionInProgress()){
            try{
                ObjectSender request = fetchEncryptedOffer();
                offerReceived = (EncryptedOffer) request.getObjectClass().cast(request.getObject());
                seller.addEncryptedOffer(offerReceived);
                seller.addBidderIp(request.getIP_sender());
                seller.addBidderPort(request.getPORT_sender());
                displayOfferReceived(offerReceived);
            } catch (IOException | ClassNotFoundException ignored){}
        }
    }

    public void diplayHello(){
        ui.diplayHello();
    }

    public void displayOfferReceived(EncryptedOffer encryptedOffer){
        ui.displayOfferReceived(encryptedOffer);
    }

    public void displayWinner(){
        List<EncryptedOffer>  encryptedOffers = seller.getEncryptedOffers();
        List<Double> biddersWinStatus = getBiddersWinStatus();
        String winnerID = this.getWinnerID(encryptedOffers,biddersWinStatus);
        Double price = winner.getPriceToPay();
        ui.displayWinner(winnerID,price);
    }

    public void displayEncryptedPriceSended(){
        ui.displayEncryptedPriceSended();
    }

    public void displayResultsSent(){
        ui.displayResultsSent();
    }

    public EncryptedPrices getEncryptedPrices(List<EncryptedOffer> encryptedOffers){
        ArrayList<byte[]> encryptedPrices = new ArrayList<>();
        for (EncryptedOffer encryptedOffer: encryptedOffers){
            encryptedPrices.add(encryptedOffer.getPrice());
        }
        return new EncryptedPrices(encryptedPrices);
    }

    public ObjectSender fetchEncryptedOffer() throws IOException, ClassNotFoundException {
        return networkController.fetchEncryptedOffer();
    }

    public void sendEncryptedPrices() throws IOException {
        networkController.sendEncryptedPrices(getEncryptedPrices(seller.getEncryptedOffers()));
    }

    public void fetchWinner() {
        winner = networkController.fetchWinner();
    }

    public void sendResults() throws IOException {
        List<String> IPs = seller.getBiddersIps();
        List<Integer> PORTs = seller.getBiddersPorts();
        List<Double> results = getBiddersWinStatus();
        if (IPs.size() == PORTs.size() && IPs.size() == results.size()){
            for (int i=0;i< IPs.size();i++){
                networkController.sendData(IPs.get(i),PORTs.get(i),results.get(i));
            }
        } else {
            throw new IllegalArgumentException("All three lists must be the same size.");
        }
    }

    public List<Double> getBiddersWinStatus(){
        List<EncryptedOffer> encryptedOffers = seller.getEncryptedOffers();
        boolean haveAWinner = false;
        List<Double> winStatus = new ArrayList<>();
        for (int i=0;i<encryptedOffers.size();i++){
            if (Arrays.equals(encryptedOffers.get(i).getPrice(), winner.getEncryptedMaxprice()) && !haveAWinner){
                winStatus.add(winner.getPriceToPay());
                haveAWinner = true;
            } else {
                winStatus.add(-1.0);
            }
        }
        return winStatus;
    }

    public String getWinnerID(List<EncryptedOffer> encryptedOffers, List<Double> biddersWinStatus){
        for (int i=0;i<encryptedOffers.size();i++){
            if (biddersWinStatus.get(i) != -1.0){
                return encryptedOffers.get(i).getIdBidder();
            }
        }
        return null;
    }

}