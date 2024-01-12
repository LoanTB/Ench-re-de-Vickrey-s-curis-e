package com.projetenchere.Manager.View;

import com.projetenchere.common.Models.Winner;
import com.projetenchere.common.View.I_UserInterface;

import java.time.LocalDateTime;

public interface IManagerUserInterface extends I_UserInterface {
    void displayHello();
    void tellSignatureConfigSetup();
    void tellSignatureConfigGeneration();
    void tellSignatureConfigReady();
    void displayBidLaunch();
    void tellManagerReadyToProcessBids();
}
