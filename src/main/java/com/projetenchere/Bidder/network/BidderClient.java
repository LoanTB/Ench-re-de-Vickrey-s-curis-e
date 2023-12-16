package com.projetenchere.Bidder.network;

import com.projetenchere.common.Models.Encrypted.EncryptedOffer;
import com.projetenchere.common.Models.Network.Communication.CurrentBids;
import com.projetenchere.common.Models.Network.Communication.WinStatus;
import com.projetenchere.common.network.*;
import com.projetenchere.common.Utils.NetworkUtil;

import javax.net.ssl.SSLSocket;
import java.net.InetSocketAddress;
import java.security.PublicKey;

public class BidderClient extends Client{

    private SSLSocket toManager;
    private SSLSocket toSeller;
    public void connectToManager() {
        SocketFactory factory = new SocketFactory();
        toManager = factory.createSocket(NetworkUtil.MANAGER_SOCKET_ADDRESS);
    }

    public void connectToSeller(InetSocketAddress sellerSocketAddress) {
        SocketFactory factory = new SocketFactory();
        toSeller = factory.createSocket(sellerSocketAddress);
    }

    public PublicKey getManagerPubKey() {
        return fetch(
                toManager,
                Headers.GET_PUB_KEY,
                Headers.OK_PUB_KEY,
                null
        );
    }

    public CurrentBids getCurrentBids() {
        return fetch(
                toManager,
                Headers.GET_CURRENT_BIDS,
                Headers.OK_CURRENT_BIDS,
                null
        );
    }

    public WinStatus sendOfferAndWaitForResult(EncryptedOffer offer) {
        return fetchWithData(
                toSeller,
                Headers.GET_WIN_STATUS,
                Headers.OK_WIN_STATUS,
                offer,
                null
        );
    }


}
