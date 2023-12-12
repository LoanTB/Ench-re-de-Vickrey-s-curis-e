package com.projetenchere.Bidder.network;

import com.projetenchere.Bidder.Controllers.BidderController;
import com.projetenchere.common.ClientSocketCommunicator;
import com.projetenchere.common.ICommunicator;
import com.projetenchere.common.Models.Bid;
import com.projetenchere.common.Models.Identity;
import com.projetenchere.common.Models.Network.Communication.Informations.NetworkContactInformation;
import com.projetenchere.common.Models.Network.Communication.Informations.PublicSecurityInformations;
import com.projetenchere.common.Controllers.NetworkController;
import com.projetenchere.common.Party;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.HashMap;

public class BidderCommunications {
    private static final InetSocketAddress MANAGER_ADDRESS = new InetSocketAddress("127.0.0.1", 24683);
    private final HashMap<Bid, Party> bidsSellerSet = new HashMap<>();
    private final Party manager;

    public BidderCommunications() {
        this.manager = new Party(MANAGER_ADDRESS);
        ICommunicator toManager = new ClientSocketCommunicator(manager);
    }


}
