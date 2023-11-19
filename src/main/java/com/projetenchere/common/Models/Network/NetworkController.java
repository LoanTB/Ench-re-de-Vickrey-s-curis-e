package com.projetenchere.common.Models.Network;

import com.projetenchere.common.Models.Network.Communication.Informations.PrivateSecurityInformations;
import com.projetenchere.common.Models.Network.Communication.Informations.PublicSecurityInformations;
import com.projetenchere.common.Models.Network.Sendable.ObjectSender;
import com.projetenchere.common.Utils.NetworkUtil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class NetworkController implements Runnable {
    private final ExecutorService executor = Executors.newFixedThreadPool(5);
    private final List<PublicSecurityInformations> informations = new ArrayList<>();
    protected PrivateSecurityInformations myInformations;

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(myInformations.getNetworkContactInformation().getPort())) {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    ObjectInputStream objectInput = new ObjectInputStream(clientSocket.getInputStream());
                    ObjectSender request = (ObjectSender) objectInput.readObject();

                    RequestHandler requestHandler = determineCommonHandler(request);
                    if (requestHandler != null) {
                        executor.submit(() -> requestHandler.handle(request));
                    }
                } catch (SocketTimeoutException | ClassNotFoundException ignored) {
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private RequestHandler determineCommonHandler(ObjectSender objectSender){
        return determineSpecificsHandler(objectSender);
    }

    protected abstract RequestHandler determineSpecificsHandler(ObjectSender objectSender);

    public void saveInformations(PublicSecurityInformations informations){
        if (this.informations.contains(informations)){
            this.informations.set(this.informations.indexOf(informations),informations);
        } else {
            this.informations.add(informations);
        }
    }

    public PublicSecurityInformations getMyInformations(){
        return new PublicSecurityInformations(myInformations);
    }

    public PublicSecurityInformations getInformationsOf(String id){
        for (PublicSecurityInformations publicSecurityInformations : this.informations){
            if (publicSecurityInformations.getId().equals(id)){
                return publicSecurityInformations;
            }
        }
        return null;
    }

    public boolean informationContainsPublicKey(String id){
        PublicSecurityInformations publicSecurityInformations = getInformationsOf(id);
        if (publicSecurityInformations != null){
            return !(publicSecurityInformations.getSignaturePublicKey() == null);
        } else {
            return false;
        }
    }

    public boolean existInformations(String id){
        return !(getInformationsOf(id) == null);
    }

    public void sendTo(String id, Object object) throws IOException {
        PublicSecurityInformations target = getInformationsOf(id);
        NetworkUtil.send(
                target.getNetworkContactInformation().getIp(),
                target.getNetworkContactInformation().getPort(),
                new ObjectSender(
                        myInformations.getNetworkContactInformation().getIp(),
                        myInformations.getNetworkContactInformation().getPort(),
                        object,
                        object.getClass()
                )
        );
    }
}
