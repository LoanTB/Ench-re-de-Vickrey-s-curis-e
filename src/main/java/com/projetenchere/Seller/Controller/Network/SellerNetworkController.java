package com.projetenchere.Seller.Controller.Network;

import com.projetenchere.Seller.Controller.Network.Handlers.EncryptedPricesRequestHandler;
import com.projetenchere.Seller.Controller.Network.Handlers.ManagerInformationsRequestHandler;
import com.projetenchere.Seller.Controller.Network.Handlers.WinnerRequestHandler;
import com.projetenchere.Seller.Controller.SellerController;
import com.projetenchere.common.Models.Encrypted.EncryptedOffer;
import com.projetenchere.common.Models.Encrypted.EncryptedPrices;
import com.projetenchere.common.Models.Network.Communication.SecurityInformations;
import com.projetenchere.common.Models.Network.NetworkContactInformation;
import com.projetenchere.common.Models.Network.RequestHandler;
import com.projetenchere.common.Models.Network.Sendable.ObjectSender;
import com.projetenchere.common.Models.Network.Communication.Winner;
import com.projetenchere.common.Utils.Network.NetworkUtil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SellerNetworkController {
    private final NetworkContactInformation sellerNCI;
    private SecurityInformations managerInformations = null;
    private final ExecutorService executor = Executors.newFixedThreadPool(5);
    private final SellerController controller;

    public SellerNetworkController(SellerController controller) throws UnknownHostException {
        this.controller = controller;
        sellerNCI = new NetworkContactInformation(NetworkUtil.getMyIP(),24682);
    }

//    public void setSellerNCI(NetworkContactInformation networkContactInformation){
//        sellerNCI = networkContactInformation;
//    } // TODO : Faire en sorte que les ports ne soient pas attribué dès le départ

    public String getSellerIp(){
        return sellerNCI.getIp();
    }

    public int getSellerPort(){
        return sellerNCI.getPort();
    }

    public void setManagerInformations(SecurityInformations managerInformations){
        this.managerInformations = managerInformations;
    }

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
        if (objectSender.getObjectClass().equals(EncryptedOffer.class) && controller.auctionInProgress()) {
            return new EncryptedPricesRequestHandler(controller);
        }
        if (objectSender.getObjectClass().equals(Winner.class) && !controller.auctionInProgress()) {
            return new WinnerRequestHandler(controller);
        }
        if (objectSender.getObjectClass().equals(SecurityInformations.class) &&
                ((SecurityInformations) objectSender.getObject()).getId().equals("Manager") &&
                managerInformations == null) {
            return new ManagerInformationsRequestHandler(this);
        }
        return null;
    }

    public void sendEncryptedPrices(EncryptedPrices encryptedPrices) throws IOException {
        NetworkUtil.send(
                managerInformations.getNetworkContactInformation().getIp(),
                managerInformations.getNetworkContactInformation().getPort(),
                new ObjectSender(
                        sellerNCI.getIp(),
                        sellerNCI.getPort(),
                        encryptedPrices,
                        encryptedPrices.getClass()
                )
        );
    }

    public void sendResults(List<String> ips, List<Integer> ports, List<Double> results) throws IOException {
        for (int i=0;i< ips.size();i++){
            NetworkUtil.send(
                    ips.get(i),
                    ports.get(i),
                    new ObjectSender(
                            sellerNCI.getIp(),
                            sellerNCI.getPort(),
                            results.get(i),
                            results.get(i).getClass()
                    )
            );
        }
    }
}