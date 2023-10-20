package com.projetenchere.Manager.Controller;

import com.projetenchere.common.Model.Bid;
import com.projetenchere.common.Model.BidStarter;
import com.projetenchere.common.Model.Winner;
import com.projetenchere.common.network.NetworkUtil;
import com.projetenchere.common.network.ObjectSender;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.PublicKey;

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

    //Recevoir les offres.
    public void fetchEncryptedPrice() //ADD : Retour : List<EncryptedOffer>
    {
        //TODO : FetchObjects ?
    }

    public void sendBidAndKey(Bid currentBid, PublicKey managerPublicKey)
    {
        BidStarter currentBidStarter = new BidStarter(managerPublicKey,currentBid);

        //TODO : SendObjects. ????
    }

    public void sendWinnerAndPrice(Winner result) throws IOException {
        ObjectSender pack = new ObjectSender(ManagerIp,ManagerPort,result,Winner.class);
        NetworkUtil.send(ManagerIp,ManagerPort,pack); //ATTENTION Destiné au seller. ip à changer à l'avenir.
    }

}