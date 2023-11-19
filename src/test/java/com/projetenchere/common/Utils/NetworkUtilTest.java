package com.projetenchere.common.Utils;

import com.projetenchere.common.Controllers.NetworkController;
import com.projetenchere.common.Handlers.InformationsRequestWithAckHandler;
import com.projetenchere.common.Handlers.InformationsRequestWithReplyHandler;
import com.projetenchere.common.Handlers.RequestHandler;
import com.projetenchere.common.Models.Network.Communication.Acknowledgment;
import com.projetenchere.common.Models.Network.Communication.Informations.NetworkContactInformation;
import com.projetenchere.common.Models.Network.Communication.Informations.PrivateSecurityInformations;
import com.projetenchere.common.Models.Network.Communication.Informations.PublicSecurityInformations;
import com.projetenchere.common.Models.Network.Communication.ObjectReceived;
import com.projetenchere.common.Models.Network.Sendable.ObjectSender;
import org.junit.jupiter.api.Test;
import java.io.IOException;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NetworkUtilTest {

    Thread serverThread;
    Thread clientThread;

    @Test
    public void test_ObjectSender_transmission() throws InterruptedException {
        final ObjectSender[] data = new ObjectSender[2];

        serverThread = new Thread(() -> {
            try {
                data[0] =  new ObjectSender(NetworkUtil.getMyIP(),24681,new String("Super message !"),String.class);
                NetworkUtil.send(NetworkUtil.getMyIP(), 24681,data[0]);
            } catch (IOException e) {
                throw new RuntimeException("Erreur côté serveur: " + e);
            }
        });

        clientThread = new Thread(() -> {
            try {
                data[1] = (ObjectSender) NetworkUtil.receive(24681,1000);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException("Erreur côté client: " + e);
            }
        });

        serverThread.start();
        clientThread.start();

        serverThread.join();
        clientThread.join();

        assertEquals(data[0].getObjectClass().cast(data[0].getObject()),data[1].getObjectClass().cast(data[1].getObject()));

        serverThread = new Thread(() -> {
            try {
                data[0] =  new ObjectSender(NetworkUtil.getMyIP(),24681,new String("Oui !"),String.class);
                NetworkUtil.send(data[1].getIP_sender(),data[1].getPORT_sender(),data[0]);
            } catch (IOException e) {
                throw new RuntimeException("Erreur côté serveur: " + e);
            }
        });

        clientThread = new Thread(() -> {
            try {
                data[1] = (ObjectSender) NetworkUtil.receive(data[1].getPORT_sender(),1000);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException("Erreur côté client: " + e);
            }
        });

        serverThread.start();
        clientThread.start();

        serverThread.join();
        clientThread.join();

        assertEquals(data[1].getObjectClass().cast(data[1].getObject()),"Oui !");
    }

    @Test
    public void test_ObjectSender_secure_transmission() throws Exception {
        final NetworkController A = new NetworkController() {
            @Override
            protected RequestHandler determineSpecificsHandler(ObjectReceived objectReceived) {
                class APRESARequestHandler implements RequestHandler {
                    private final NetworkController networkController;
                    public APRESARequestHandler(NetworkController networkController) {this.networkController = networkController;}
                    @Override
                    public void handle(ObjectReceived objectReceived) throws Exception {
                        networkController.sendTo(objectReceived.getAuthenticationStatus().authorOfSignature(),"Il y a B, et Après B il y a quoi ?");
                    }
                }

                class FinalRequestHandler implements RequestHandler {
                    private final NetworkController networkController;
                    public FinalRequestHandler(NetworkController networkController) {this.networkController = networkController;}
                    @Override
                    public void handle(ObjectReceived objectReceived) throws Exception {
                        networkController.setMyInformations(new PrivateSecurityInformations("A",new NetworkContactInformation("127.0.0.1",24683),EncryptionUtil.generateKeyPair(),EncryptionUtil.generateKeyPair()));
                    }
                }

                if (objectReceived.getObjectSended().getObjectClass().equals(PublicSecurityInformations.class)){
                    System.out.println("A : Sauvegarde les infos de B et répond ses infos");
                    return new InformationsRequestWithReplyHandler(this);
                }
                if (objectReceived.getAuthenticationStatus() != null && objectReceived.getObjectSended().getObject().equals("Il y a quoi après A ?")){
                    System.out.println("A (SECURE) : répond Il y a B, et Après B il y a quoi ?");
                    return new APRESARequestHandler(this);
                }
                if (objectReceived.getAuthenticationStatus() != null && objectReceived.getAuthenticationStatus().authorOfSignature().equals("B") && objectReceived.getObjectSended().getObject().equals("feur")){
                    System.out.println("A <Action> : Change de clés");
                    return new FinalRequestHandler(this);
                }
                if (objectReceived.getAuthenticationStatus() != null && objectReceived.getAuthenticationStatus().authorOfSignature().equals("B") && objectReceived.getObjectSended().getObject().equals("Allo ?")){
                    System.out.println("A <Action> : Ne fait plus rien en espérant que B se taise");
                }
                return null;
            }
        };

        final NetworkController B = new NetworkController() {
            @Override
            protected RequestHandler determineSpecificsHandler(ObjectReceived objectReceived) {
                class APRESBRequestHandler implements RequestHandler {
                    private final NetworkController networkController;
                    public APRESBRequestHandler(NetworkController networkController) {this.networkController = networkController;}
                    @Override
                    public void handle(ObjectReceived objectReceived) throws Exception {
                        networkController.sendTo(objectReceived.getAuthenticationStatus().authorOfSignature(),"feur");
                    }
                }

                if (objectReceived.getObjectSended().getObjectClass().equals(PublicSecurityInformations.class)){
                    System.out.println("B : Sauvegarde A et répond ACK");
                    return new InformationsRequestWithAckHandler(this);
                }
                if (objectReceived.getAuthenticationStatus() != null && objectReceived.getObjectSended().getObject().equals("Il y a B, et Après B il y a quoi ?")){
                    System.out.println("B (SECURE) : Répond feur");
                    return new APRESBRequestHandler(this);
                }
                return null;
            }
        };

        A.setMyInformations(new PrivateSecurityInformations("A",new NetworkContactInformation("127.0.0.1",24683),EncryptionUtil.generateKeyPair(),EncryptionUtil.generateKeyPair()));
        B.setMyInformations(new PrivateSecurityInformations("B",new NetworkContactInformation("127.0.0.1",24684),EncryptionUtil.generateKeyPair(),EncryptionUtil.generateKeyPair()));

        Thread threadA = new Thread(A);
        threadA.start();

        Thread threadB = new Thread(B);
        threadB.start();

        sleep(3000);

        B.saveInformations(new PublicSecurityInformations("A",new NetworkContactInformation("127.0.0.1",24683),null,null));
        System.out.println("B : Envoie ses informations à A");
        B.sendTo("A",B.getMyPublicInformations());
        sleep(3000);
        System.out.println("B (SECURE) : dit à A Il y a quoi après A ?");
        B.sendTo("A","Il y a quoi après A ?");
        sleep(3000);
        System.out.println("B (SECURE) : Allo ?");
        B.sendTo("A","Allo ?");
        System.out.println("B (SECURE) : Envoie ses informations à A");
        B.sendTo("A",B.getMyPublicInformations());
        sleep(3000);
        System.out.println("B : Supprime les clés de A");
        System.out.println("B : Allo ?");
        B.sendTo("A","Allo ?");
        B.saveInformations(new PublicSecurityInformations(B.getInformationsOf("A").getId(),B.getInformationsOf("A").getNetworkContactInformation(),null,null));
        System.out.println("B : Envoie ses informations à A");
        B.sendTo("A",B.getMyPublicInformations());
        sleep(3000);
        System.out.println("B (SECURE) : Allo ?");
        B.sendTo("A","Allo ?");
        sleep(2000);
    }
}