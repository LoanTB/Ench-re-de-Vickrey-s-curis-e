package com.projetenchere.Manager.Controller.Network;

import com.projetenchere.Manager.Controller.ManagerController;
import com.projetenchere.Manager.Controller.Network.Handlers.InitPackageRequestHandler;
import com.projetenchere.Manager.Controller.Network.Handlers.PriceDecryptionRequestHandler;
import com.projetenchere.common.Models.Bid;
import com.projetenchere.common.Models.Network.Communication.CurrentBids;
import com.projetenchere.common.Models.Encrypted.EncryptedPrices;
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
import java.security.PublicKey;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ManagerNetworkController {
    final private NetworkContactInformation managerNCI;
    private final ExecutorService executor = Executors.newFixedThreadPool(5);
    private CurrentBids currentBids = null;
    private final ManagerController managerController;

    public ManagerNetworkController(ManagerController managerController) throws UnknownHostException {
        this.managerController = managerController;
        managerNCI = new NetworkContactInformation(NetworkUtil.getMyIP(),24683);
    }

    public void savePublicKey(PublicKey publicKey){
        currentBids = new CurrentBids(publicKey);
    }

    public String getManagerIp() {
        return managerNCI.getIp();
    }

    public int getManagerPort() {
        return managerNCI.getPort();
    }

    public void addBid(Bid bid){
        currentBids.addCurrentBid(bid);
    }

    public void startAllBids(){
        currentBids.startAllBids();
    }

    public void startBid(int id) {
        currentBids.startBids(id);
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
        if (objectSender.getObject().equals("InitPackageRequest")) {
            return new InitPackageRequestHandler(currentBids,managerNCI);
        }
        if (objectSender.getObjectClass() == EncryptedPrices.class && currentBids.isOver(((EncryptedPrices)objectSender.getObject()).getBidId())) {
            return new PriceDecryptionRequestHandler(managerController,managerNCI);
        }
        return null;
    }

    public void sendBidToSeller(Bid bid) throws IOException {
        NetworkUtil.send(bid.getSellerIp(),
                bid.getSellerPort(),
                new ObjectSender(getManagerIp(),
                        getManagerPort(),
                        bid,
                        Bid.class
                )
        );
    }
}