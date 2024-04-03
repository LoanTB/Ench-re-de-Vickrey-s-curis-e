package com.projetenchere.Seller.Loader;

import com.projetenchere.Seller.Controllers.SellerController;
import com.projetenchere.Seller.View.ISellerUserInterface;
import com.projetenchere.Seller.View.commandLineInterface.SellerCommandLineInterface;
import javafx.application.Platform;

public class SellerCommandLineApp{

    public static void launchApp() throws Exception {
        SellerController controllerInstance = new SellerController((ISellerUserInterface) new SellerCommandLineInterface());
        controllerInstance.displayHello();
        controllerInstance.setSignatureConfig();
        controllerInstance.createMyBid();
        controllerInstance.sendMyBid();
        controllerInstance.receiveOkUntilCheckEndAndSendResults();
        try {
            controllerInstance.sendEncryptedOffersSet();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Platform.runLater(controllerInstance::displayWinner);
    }
}