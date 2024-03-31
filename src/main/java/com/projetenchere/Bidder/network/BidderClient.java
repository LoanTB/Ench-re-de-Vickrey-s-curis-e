package com.projetenchere.Bidder.network;

import com.projetenchere.common.Models.CurrentBids;
import com.projetenchere.common.Models.EndPack;
import com.projetenchere.common.Models.PlayerStatus;
import com.projetenchere.common.Models.SignedPack.SigPack_EncOffer;
import com.projetenchere.common.Models.SignedPack.SigPack_EncOffersProduct;
import com.projetenchere.common.Models.SignedPack.SigPack_Confirm;
import com.projetenchere.common.Utils.NetworkUtil;
import com.projetenchere.common.network.Client;
import com.projetenchere.common.network.ClientSocketWrapper;
import com.projetenchere.common.network.Headers;
import com.projetenchere.common.network.socket.SSLSocketFactory;

import java.net.InetSocketAddress;
import java.security.PublicKey;

public class BidderClient extends Client {

    private ClientSocketWrapper toManager;
    private ClientSocketWrapper toSeller;

    public void connectToManager() {
        SSLSocketFactory factory = new SSLSocketFactory();
        toManager = new ClientSocketWrapper(factory.createSocket(NetworkUtil.MANAGER_SOCKET_ADDRESS));
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

    public SigPack_EncOffersProduct sendOfferReceiveList(SigPack_EncOffer offer) {
        return fetchWithData(
                toSeller,
                Headers.SEND_OFFER,
                Headers.CHECK_LIST,
                offer
        );
    }

    public EndPack validateAndGetResults(SigPack_Confirm key) {
        return fetchWithData(
                toSeller,
                Headers.GET_RESULTS,
                Headers.OK_RESULTS,
                key
        );
    }

    public PlayerStatus sendExpressWin(SigPack_Confirm expression){
        return fetchWithData(
                toSeller,
                Headers.SET_WIN_EXP,
                Headers.OK_WIN_EXP,
                expression
        );
    }

}
