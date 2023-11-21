package com.projetenchere.Bidder.View.commandLineInterface;

import com.projetenchere.Bidder.Model.Bidder;
import com.projetenchere.Bidder.View.IBidderUserInterface;
import com.projetenchere.common.Models.Network.Communication.CurrentBids;
import com.projetenchere.common.Models.Offer;

import java.util.Scanner;

public class BidderCommandLineInterface implements IBidderUserInterface {
    public static final Scanner scanner = new Scanner(System.in);

    public BidderCommandLineInterface() {
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    private String readMessage() {
        return scanner.nextLine();
    }

    @Override
    public void displayBid(CurrentBids currentBids) {
        showMessage("Bienvenue!");
        showMessage("Enchères Actuelle :");
        showMessage(currentBids.toString()+"\n");
    }

    @Override
    public Offer readOffer(Bidder bidder) {
        showMessage("Quel est l'identifiant de l'enchère sur laquelle vous voulez enchérir ?");
        String idBidString = readMessage();
        showMessage("Quel est votre prix ?");
        String offerString = readMessage();
        return new Offer(bidder.getIdentity().getId(), idBidString, offerString);
    }

    @Override
    public void tellOfferWon(double priceToPay) {
        showMessage("Votre offre a gagné, vous devez payer " + priceToPay + "€");
    }


    @Override
    public void tellOfferLost() {
        showMessage("Votre offre a perdu");
    }

    @Override
    public void tellOfferSent(){
        showMessage("Votre offre a bien été envoyé");
    }

    @Override
    public void tellWaitOfferResult(){
        showMessage("Attente des résultats...");
    }

    @Override
    public void tellWaitBidsAnnoncement() {
        showMessage("Attente de reception des enchères en cours...");
    }

    @Override
    public void tellWaitBidsPublicKeysAnnoncement() {
        showMessage("Attente/Verification de reception des clés des enchères en cours...");
    }

    @Override
    public void tellWaitManager() {
        showMessage("Le gestionnaire des enchères semble indisponible, attente du gestionnaire...");
    }

    @Override
    public void tellManagerFound() {
        showMessage("Contacter le gestionnaire établie !");
    }

    @Override
    public String readName() {
        showMessage("Quel est votre prénom ?");
        return readMessage();
    }

    @Override
    public String readSurname() {
        showMessage("Quel est votre nom ?");
        return readMessage();
    }

    @Override
    public void tellWaitManagerSecurityInformations() {
        showMessage("Attente des informations de sécurité du gestionnaire...");
    }

    @Override
    public void displayHello() {
        showMessage("Bienvenue enchérisseur !");
    }

    @Override
    public void tellReceivingInformationOf(String id, String type) {
        showMessage("Reception d'information de "+type+" "+id);
    }

    @Override
    public void tellReceiptOfCurrentBids() {
        showMessage("Reception des enchères actuelles");
    }

    @Override
    public void tellReceiptOfEncryptionKeysForCurrentBids() {
        showMessage("Reception des clés de chiffrement des enchères actuelles");
    }

    @Override
    public void tellReceiptOfBidResult(String id) {
        showMessage("Reception des résultats de l'enchère "+id);
    }

    @Override
    public void tellSendRequestOffers() {
        showMessage("Envoie de la demande d'enchères actuelles au gestionnaire");
    }

    @Override
    public int readPort() {
        showMessage("Quel port voulez-vous utiliser ? (49152 à 65535)");
        String portString = readMessage();
        int port = Integer.parseInt(portString);
        while (port < 49152 || port > 65535){
            showMessage("Port invalide, entrez un port valide (entre 49152 et 65535) :");
            portString = readMessage();
            port = Integer.parseInt(portString);
        }
        return port;
    }


}
