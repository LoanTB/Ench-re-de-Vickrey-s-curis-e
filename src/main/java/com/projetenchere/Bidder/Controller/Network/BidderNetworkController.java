package com.projetenchere.Bidder.Controller.Network;

import com.projetenchere.Bidder.Controller.BidderController;
import com.projetenchere.Bidder.Controller.Network.Handlers.WinStatusRequestHandler;
import com.projetenchere.Seller.Controller.Network.Handlers.ManagerInformationsRequestHandler;
import com.projetenchere.common.Models.Network.Communication.CurrentBids;
import com.projetenchere.common.Models.Encrypted.EncryptedOffer;
import com.projetenchere.common.Models.Network.Communication.SecurityInformations;
import com.projetenchere.common.Models.Network.Communication.WinStatus;
import com.projetenchere.common.Models.Network.NetworkContactInformation;
import com.projetenchere.common.Models.Network.RequestHandler;
import com.projetenchere.common.Models.Network.Sendable.ObjectSender;
import com.projetenchere.common.Utils.Network.NetworkUtil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BidderNetworkController {
    private final NetworkContactInformation bidderNCI;
    private final ExecutorService executor = Executors.newFixedThreadPool(5);
    private final BidderController controller;
    private SecurityInformations managerInformations = new SecurityInformations(new NetworkContactInformation("127.0.0.1",24683));

    private SecurityInformations sellerInformations = new SecurityInformations(new NetworkContactInformation("127.0.0.1",24682));

    public BidderNetworkController(BidderController bidderController) throws UnknownHostException {
        controller = bidderController;
        bidderNCI = new NetworkContactInformation(NetworkUtil.getMyIP(),24683);
    }

    public void setManagerInformations(SecurityInformations managerInformations) {
        if (this.managerInformations == null){
            this.managerInformations = managerInformations;
        }
    }

    public SecurityInformations getManagerInformations() {
        return managerInformations;
    }

    public void setSellerInformations(SecurityInformations managerInformations) {
        if (this.sellerInformations.getId() == null){
            this.sellerInformations = managerInformations;
        }
    }

    public SecurityInformations getSellerInformations() {
        return sellerInformations;
    }

    //    private String myIp() { // Ne sert pas mais bonne idée alors je laisse
//        try {
//            return NetworkUtil.getMyIP();
//        } catch (UnknownHostException e) {
//            return "localhost";
//        }
//    }

    public void startListening(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Socket clientSocket = serverSocket.accept();
                ObjectInputStream objectInput = new ObjectInputStream(clientSocket.getInputStream());
                ObjectSender request = (ObjectSender) objectInput.readObject();

                RequestHandler requestHandler = determineHandler(request);
                if (requestHandler != null) {
                    executor.submit(() -> requestHandler.handle(request));
                }
            } catch (SocketTimeoutException | ClassNotFoundException ignored) {
            }
        }
        serverSocket.close();
    }

    private RequestHandler determineHandler(ObjectSender objectSender) {
        if (objectSender.getObjectClass().equals(WinStatus.class) &&
                controller.getParticipatedBid().contains(((WinStatus) objectSender.getObject()).getBidId())) {
            return new WinStatusRequestHandler(controller);
        }
        if (objectSender.getObjectClass().equals(SecurityInformations.class) &&
                ((SecurityInformations) objectSender.getObject()).getId().equals("Manager") &&
                managerInformations.getId() == null) {
            return new WinStatusRequestHandler(controller);
        }
        return null;
    }


    public void askForInitPackage(int localPort) throws IOException, ClassNotFoundException {
        NetworkUtil.send(
                managerInformations.getNetworkContactInformation().getIp(),
                managerInformations.getNetworkContactInformation().getPort(),
                new ObjectSender(
                        bidderNCI.getIp(),
                        bidderNCI.getPort(),
                        "InitPackageRequest",
                        String.class
                )
        );
        ObjectSender objectSender = NetworkUtil.receive(localPort,30000); // On garde cette façon de recevoir car le blockage est utile
        if (!objectSender.getObjectClass().equals(CurrentBids.class)) {
            throw new ClassNotFoundException("Received wrong class");
        } else {
            CurrentBids currentBids = (CurrentBids) objectSender.getObject();
            managerInformations = currentBids.getManagerInformations();
            controller.setCurrentBids(currentBids);
        }
    }

    public void sendOffer(EncryptedOffer encryptedOffer) throws IOException {
        NetworkUtil.send(
                sellerInformations.getNetworkContactInformation().getIp(),
                sellerInformations.getNetworkContactInformation().getPort(),
                new ObjectSender(
                        bidderNCI.getIp(),
                        bidderNCI.getPort(),
                        encryptedOffer,
                        EncryptedOffer.class
                )
        );
    }
}
