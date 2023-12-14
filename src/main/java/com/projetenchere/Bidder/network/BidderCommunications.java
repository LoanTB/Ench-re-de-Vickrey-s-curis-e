package com.projetenchere.Bidder.network;

import com.projetenchere.Manager.Model.Manager;
import com.projetenchere.common.network.*;
import com.projetenchere.common.network.socket.ClientSocketCommunicator;
import com.projetenchere.common.Models.Bid;
import com.projetenchere.common.Utils.NetworkUtil;

import java.security.PublicKey;
import java.util.HashMap;

public class BidderCommunications {
    private final HashMap<Bid, Party> bidsSellerSet = new HashMap<>();
    private final IClientCommunicator toManager;

    public BidderCommunications() {
        Party manager = new Manager(NetworkUtil.getManagerSocketAddress());
        this.toManager = new ClientSocketCommunicator(manager);
    }

    public PublicKey getManagerPubKey() {
       DataWrapper<PublicKey> wrappedData = toManager.requestData(NetworkDataHeaders.GET_PUB_KEY, NetworkDataHeaders.OK_PUB_KEY);
       return wrappedData.unwrap();
    }


}
