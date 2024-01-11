package com.projetenchere.Bidder.network;

import com.projetenchere.common.Models.Encrypted.EncryptedOffer;
import com.projetenchere.common.Models.CurrentBids;
import com.projetenchere.common.Models.Encrypted.SignedEncryptedOfferSet;
import com.projetenchere.common.Models.Encrypted.SignedPublicKey;
import com.projetenchere.common.Models.WinStatus;
import com.projetenchere.common.network.*;
import com.projetenchere.common.Utils.NetworkUtil;
import com.projetenchere.common.network.socket.SSLSocketFactory;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.PublicKey;

public class BidderClient extends Client{

    private ClientSocketWrapper toManager;
    private ClientSocketWrapper toSeller;
    public void connectToManager() {
        SSLSocketFactory factory = new SSLSocketFactory();
        toManager = new ClientSocketWrapper(factory.createSocket(NetworkUtil.MANAGER_SOCKET_ADDRESS));
        System.out.println("iudvecu");
    }

    public void connectToSeller(InetSocketAddress sellerSocketAddress) {
        SSLSocketFactory factory = new SSLSocketFactory();
        toSeller = new ClientSocketWrapper((factory.createSocket(sellerSocketAddress)));
    }

    public void stopManager() {
        stop(toManager);
    }

    public void stopSeller() {
        stop(toSeller);
    }

    public PublicKey getManagerPubKey() {
        return fetch(
                toManager,
                Headers.GET_PUB_KEY,
                Headers.OK_PUB_KEY
        );
    }

    public CurrentBids getCurrentBids() {
        return fetch(
                toManager,
                Headers.GET_CURRENT_BIDS,
                Headers.OK_CURRENT_BIDS
        );
    }

    public SignedEncryptedOfferSet sendOfferReceiveList(EncryptedOffer offer) {
        return fetchWithData(
                toSeller,
                Headers.SEND_OFFER,
                Headers.CHECK_LIST,
                offer
        );
    }

    public WinStatus validateAndGetWinStatus(SignedPublicKey key) {
        return fetchWithData(
                toSeller,
                Headers.GET_WIN_STATUS,
                Headers.OK_WIN_STATUS,
                key
        );
    }

    public void stopEverything() {
        stopSeller();
        abort(toManager);
        abort(toSeller);
    }


}
