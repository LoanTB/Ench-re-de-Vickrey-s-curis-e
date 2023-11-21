package com.projetenchere.common.Controllers;

import com.projetenchere.common.Models.Identity;
import com.projetenchere.common.Models.Network.Communication.AuthenticationStatus;
import com.projetenchere.common.Models.Network.Communication.Informations.NetworkContactInformation;
import com.projetenchere.common.Models.Network.Communication.Informations.PrivateSecurityInformations;
import com.projetenchere.common.Models.Network.Communication.Informations.PublicSecurityInformations;
import com.projetenchere.common.Handlers.RequestHandler;
import com.projetenchere.common.Models.Network.Communication.ObjectReceived;
import com.projetenchere.common.Models.Network.Sendable.ObjectSender;
import com.projetenchere.common.Models.Network.Sendable.SecuredObjectSender;
import com.projetenchere.common.Utils.EncryptionUtil;
import com.projetenchere.common.Utils.NetworkUtil;
import com.projetenchere.common.Utils.SerializationUtil;
import com.projetenchere.common.Utils.SignatureUtil;

import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
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
        try (ServerSocket serverSocket = new ServerSocket(myInformations.networkContactInformation().port())) {
            while (!Thread.currentThread().isInterrupted()) {
                ObjectReceived request = null;
                try {
                    Socket clientSocket = serverSocket.accept();
                    ObjectInputStream objectInput = new ObjectInputStream(clientSocket.getInputStream());
                    Object object = objectInput.readObject();
                    if (object instanceof ObjectSender) {
                        request = new ObjectReceived((ObjectSender) object);
                    } else if (object instanceof SecuredObjectSender securedRequest){
                        for (PublicSecurityInformations securityInformations : informations){
                            if (SignatureUtil.verifyDataSignature(
                                    securedRequest.getEncryptedObjectSender(),
                                    securedRequest.getSignatureBytes(),
                                    securityInformations.getSignaturePublicKey()))
                            {
                                request = new ObjectReceived(
                                        (ObjectSender) SerializationUtil.deserialize(
                                                EncryptionUtil.decrypt(
                                                        securedRequest.getEncryptedObjectSender(),
                                                        myInformations.encryptionKeys().getPrivate()
                                                )
                                        ),
                                        new AuthenticationStatus("OK",securityInformations.getIdentity())
                                );
                            }
                        }
                    } else {
                        throw new InvalidClassException("Received object is not an instance of ObjectSender");
                    }

                    RequestHandler requestHandler = determineCommonHandler(request);
                    if (requestHandler != null) {
                        ObjectReceived finalRequest = request;
                        executor.submit(() -> {
                            try {
                                requestHandler.handle(finalRequest);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        });
                    }
                } catch (Exception ignored){}
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private RequestHandler determineCommonHandler(ObjectReceived objectReceived){
        return determineSpecificsHandler(objectReceived);
    }

    protected abstract RequestHandler determineSpecificsHandler(ObjectReceived objectReceived);

    public boolean isAuthenticated(ObjectReceived objectReceived){
        return (objectReceived.getAuthenticationStatus() != null & objectReceived.getAuthenticationStatus().status().equals("OK"));
    }

    public boolean isAuthenticatedBy(String id,ObjectReceived objectReceived){
        return (isAuthenticated(objectReceived) && objectReceived.getAuthenticationStatus().authorOfSignature().getId().equals(id));
    }

    public boolean isAuthenticatedByType(String type,ObjectReceived objectReceived){
        return (isAuthenticated(objectReceived) && objectReceived.getAuthenticationStatus().authorOfSignature().getType().equals(type));
    }

    public boolean isAuthenticatedByAny(List<String> ids,ObjectReceived objectReceived){
        if (isAuthenticated(objectReceived)){
            for (String id:ids){
                if (objectReceived.getAuthenticationStatus().authorOfSignature().getId().equals(id)){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isAuthenticatedByAnyKnowledge(ObjectReceived objectReceived){
        List<String> ids = new ArrayList<>();
        for (PublicSecurityInformations info:informations){
            ids.add(info.getIdentity().getId());
        }
        if (isAuthenticated(objectReceived)){
            for (String id:ids){
                if (objectReceived.getAuthenticationStatus().authorOfSignature().getId().equals(id)){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isAuthenticatedByAnyKnowledgeOfType(String type,ObjectReceived objectReceived){
        List<String> ids = new ArrayList<>();
        for (PublicSecurityInformations info:informations){
            if (info.getIdentity().getType().equals(type)){
                ids.add(info.getIdentity().getId());
            }
        }
        if (isAuthenticated(objectReceived)){
            for (String id:ids){
                if (objectReceived.getAuthenticationStatus().authorOfSignature().getId().equals(id)){
                    return true;
                }
            }
        }
        return false;
    }

    public void saveInformations(PublicSecurityInformations informations){
        if (this.informations.contains(informations)){
            this.informations.set(this.informations.indexOf(informations),informations);
        } else {
            this.informations.add(informations);
        }
    }

    public PublicSecurityInformations getMyPublicInformations(){
        return new PublicSecurityInformations(myInformations);
    }

    public void setMyInformations(PrivateSecurityInformations privateSecurityInformations){
        myInformations = privateSecurityInformations;
    }

    public void setMyInformations(Identity identity, NetworkContactInformation networkContactInformation) throws Exception {
        myInformations = new PrivateSecurityInformations(identity, networkContactInformation, EncryptionUtil.generateKeyPair(),EncryptionUtil.generateKeyPair());;
    }

    public void setMyInformationsWithPort(Identity identity, int port) throws Exception {
        myInformations = new PrivateSecurityInformations(identity, new NetworkContactInformation(NetworkUtil.getMyIP(),port), EncryptionUtil.generateKeyPair(),EncryptionUtil.generateKeyPair());;
    }

    public PublicSecurityInformations getInformationsOf(String id){
        for (PublicSecurityInformations publicSecurityInformations : this.informations){
            if (publicSecurityInformations.getIdentity().getId().equals(id)){
                return publicSecurityInformations;
            }
        }
        return null;
    }

    public boolean informationContainsPublicKeys(String id){
        PublicSecurityInformations publicSecurityInformations = getInformationsOf(id);
        if (publicSecurityInformations != null){
            return !(publicSecurityInformations.getSignaturePublicKey() == null || publicSecurityInformations.getEncryptionPublicKey() == null);
        } else {
            return false;
        }
    }

    public boolean alreadyKnowTheInformation(PublicSecurityInformations publicSecurityInformations){
        PublicSecurityInformations savedPublicSecurityInformations = getInformationsOf(publicSecurityInformations.getIdentity().getId());
        if (savedPublicSecurityInformations != null){
            return !(savedPublicSecurityInformations.getSignaturePublicKey() == null || savedPublicSecurityInformations.getEncryptionPublicKey() == null);
        } else {
            return false;
        }
    }

    public boolean existInformations(String id){
        return !(getInformationsOf(id) == null);
    }

    public void sendMySI(String id) throws Exception {
        sendTo(id, getMyPublicInformations());
    }

    public void sendTo(String id, Object object) throws Exception {
        PublicSecurityInformations target = getInformationsOf(id);
        if (informationContainsPublicKeys(id)){
            NetworkUtil.send(
                    target.getNetworkContactInformation().ip(),
                    target.getNetworkContactInformation().port(),
                    new SecuredObjectSender(
                            new ObjectSender(
                                    myInformations.networkContactInformation().ip(),
                                    myInformations.networkContactInformation().port(),
                                    object,
                                    object.getClass()),
                            myInformations.signatureKeys().getPrivate(),
                            target.getEncryptionPublicKey()
                    )
            );
        } else {
            NetworkUtil.send(
                    target.getNetworkContactInformation().ip(),
                    target.getNetworkContactInformation().port(),
                    new ObjectSender(
                            myInformations.networkContactInformation().ip(),
                            myInformations.networkContactInformation().port(),
                            object,
                            object.getClass()
                    )
            );
        }

    }
}
