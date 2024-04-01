package com.projetenchere.Manager.View.commandLineInterface;

import com.projetenchere.Manager.View.IManagerUserInterface;
import com.projetenchere.common.Models.AbstractUserInterface;


public class ManagerCommandLineInterface extends AbstractUserInterface implements IManagerUserInterface {

    public ManagerCommandLineInterface() {
    }

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

    }

    @Override
    public void tellKeysReady() {

    }

    @Override
    public void tellManagerReadyToProcessBids() {
        showMessage("Gestionnaire prêt à traiter des enchères");
    }

}
