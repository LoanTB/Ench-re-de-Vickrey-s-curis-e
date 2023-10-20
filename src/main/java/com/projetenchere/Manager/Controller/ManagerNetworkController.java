package com.projetenchere.Manager.Controller;

import com.projetenchere.common.Model.Bid;
import com.projetenchere.common.Model.BidStarter;
import com.projetenchere.common.Model.Encrypted.EncryptedOffer;
import com.projetenchere.common.Model.Winner;
import com.projetenchere.common.network.NetworkUtil;
import com.projetenchere.common.network.ObjectSender;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.PublicKey;
import java.util.Set;

public class ManagerNetworkController {
    private String ManagerIp;
    private int ManagerPort = 2468;

    public String getManagerIp() {
        return ManagerIp;
    }

    public void setManagerIp(String managerIp) {
        ManagerIp = managerIp;
    }

    public ManagerNetworkController() throws UnknownHostException {
        String ManagerIp = NetworkUtil.getMyIP();
        setManagerIp(ManagerIp);
    }

    public void waitAskInitPackByBidder(BidStarter currentBidStarter) throws IOException, ClassNotFoundException {
       //TODO : Attendre toutes les requêtes ????
        ObjectSender request = NetworkUtil.receive(ManagerPort);
        if(request.getObject() == "getBidderInitPackage"){
            sendBidAndKey(currentBidStarter);
        }
    }

    public void sendBidAndKey(BidStarter currentStarter) throws IOException {
        ObjectSender pack = new ObjectSender(ManagerIp,ManagerPort,currentStarter,BidStarter.class);
        NetworkUtil.send(ManagerIp,ManagerPort,pack);
    }

    public Set<EncryptedPrice> fetchEncryptedPrice() throws IOException, ClassNotFoundException {
        ObjectSender request = NetworkUtil.receive(ManagerPort);
        if(request.getObjectClass() == Set<EncryptedPrice>.class)
        {
            Set<EncryptedPrice> pack = request.getObject();
            return pack;
        }
        return null;
    }

    public void sendWinnerAndPrice(Winner result) throws IOException {
        ObjectSender pack = new ObjectSender(ManagerIp,ManagerPort,result,Winner.class);
        NetworkUtil.send(ManagerIp,ManagerPort,pack); //ATTENTION Destiné au seller. ip à changer à l'avenir.
    }

}