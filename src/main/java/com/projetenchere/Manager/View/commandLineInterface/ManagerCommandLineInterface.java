package com.projetenchere.Manager.View.commandLineInterface;

import com.projetenchere.Manager.View.IManagerUserInterface;
import com.projetenchere.common.Models.AbstractUserInterface;
import com.projetenchere.common.Models.Bid;


public class ManagerCommandLineInterface extends AbstractUserInterface implements IManagerUserInterface {
    @Override
    public void tellSignatureConfigSetup() {
        showMessage("Mise en place de la configuration de la signature...");
    }

    @Override
    public void tellSignatureConfigGeneration() {
        showMessage("Génération de la configuration de la signature ...");
    }

    @Override
    public void tellSignatureConfigReady() {
        showMessage("Configuration de la signature terminée.");
    }

    @Override
    public void displayHello() {
        showMessage("Bienvenue Manager !");
    }

    @Override
    public void tellKeysGeneration() {
        showMessage("Génération des clés...");
    }

    @Override
    public void tellKeysReady() {
        showMessage("Paire de clés prêtes.");
    }

    @Override
    public void diplayEndBid(String idBid) {
        showMessage("L'enchère " + idBid + " a été résolue.");
    }

    @Override
    public void tellManagerReadyToProcessBids() {
        showMessage("Gestionnaire prêt à traiter des enchères");
    }

    @Override
    public synchronized void tellFalsifiedSignatureBidder() {
        showMessage("Signature de l'enchérisseur usurpée.");
    }

    @Override
    public synchronized void tellFalsifiedSignatureSeller() {
        showMessage("Signature du vendeur usurpée.");
    }

    @Override
    public synchronized void displayBidderAskBids() {
        showMessage("Un enchérisseur a demandé les enchères actuelles.");
    }

    @Override
    public void displaySendBidderPubKey() {
        showMessage("Envoi des informations de sécurité.");
    }

    @Override
    public void diplayEndBid(String idBid) {
        showMessage("L'enchère " + idBid + " a été résolue.");
    }

    @Override
    public void displayNewBid(Bid bid) {
        showMessage("Nouvelle enchère reçue : " + bid.getName() + " (" + bid.getId() + ") Date:" + bid.getStartDateTime().toString());
    }

    @Override
    public void tellBidRequest(){showMessage("Un enchérisseur a demandé les enchères actuelles.");}

    @Override
    public void displayNewBid(Bid bid) {
        showMessage("Nouvelle enchère reçue : " + bid.getName() + " (" + bid.getId() + ") Date:" + bid.getStartDateTime().toString());
    }
}
