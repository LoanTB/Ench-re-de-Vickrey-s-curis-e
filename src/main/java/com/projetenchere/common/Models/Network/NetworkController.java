package com.projetenchere.common.Models.Network;

import com.projetenchere.common.Models.Network.Communication.NetworkContactInformation;
import com.projetenchere.common.Models.Network.Communication.SecurityInformations;
import com.projetenchere.common.Models.Network.Handlers.InformationsRequestHandler;
import com.projetenchere.common.Models.Network.Sendable.ObjectSender;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class NetworkController {
    private final ExecutorService executor = Executors.newFixedThreadPool(5);
    private final List<SecurityInformations> informations = new ArrayList<>();
    protected NetworkContactInformation myNCI;

    public void startListening() throws IOException {
        ServerSocket serverSocket = new ServerSocket(myNCI.getPort());
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
        serverSocket.close();
    }

    private RequestHandler determineCommonHandler(ObjectSender objectSender){
        if (objectSender.getObjectClass().equals(SecurityInformations.class) &&
                !informationContainsPublicKey(((SecurityInformations) objectSender.getObject()).getId())) {
            return new InformationsRequestHandler(this);
        }
        return determineSpecificsHandler(objectSender);
    }

    protected RequestHandler determineSpecificsHandler(ObjectSender objectSender){
        return null;
    }

    public void saveInformations(SecurityInformations informations){
        if (this.informations.contains(informations)){
            this.informations.set(this.informations.indexOf(informations),informations);
        } else {
            this.informations.add(informations);
        }
    }

    public void saveMyInformations(PublicKey publicKey){
        saveInformations(new SecurityInformations("MyInformations",myNCI,publicKey));
    }

    public SecurityInformations getMyInformations(){
        return getInformationsOf("MyInformations");
    }

    public SecurityInformations getInformationsOf(String id){
        for (SecurityInformations securityInformations : this.informations){
            if (securityInformations.getId().equals(id)){
                return securityInformations;
            }
        }
        return null;
    }

    public boolean informationContainsPublicKey(String id){
        SecurityInformations securityInformations = getInformationsOf(id);
        if (securityInformations != null){
            return !(securityInformations.getPublicKey() == null);
        } else {
            return false;
        }
    }

    public boolean existInformations(String id){
        return !(getInformationsOf(id) == null);
    }
}
