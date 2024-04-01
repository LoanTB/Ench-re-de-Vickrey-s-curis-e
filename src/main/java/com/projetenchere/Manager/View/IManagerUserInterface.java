package com.projetenchere.Manager.View;

import com.projetenchere.common.View.I_UserInterface;

public interface IManagerUserInterface extends I_UserInterface {
    void displayHello();

    void tellKeysGeneration();

    void tellKeysReady();

    void tellSignatureConfigSetup();

    void tellSignatureConfigGeneration();

    void tellSignatureConfigReady();

    void tellManagerReadyToProcessBids();
}
