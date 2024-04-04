package com.projetenchere.seller.network;

import com.projetenchere.common.model.signedPack.SigPack_Bid;
import com.projetenchere.common.model.signedPack.SigPack_EncOffersProduct;
import com.projetenchere.common.model.signedPack.SigPack_PriceWin;
import com.projetenchere.common.network.Client;
import com.projetenchere.common.network.ClientSocketWrapper;
import com.projetenchere.common.network.Headers;
import com.projetenchere.common.network.socket.SSLSocketFactory;
import com.projetenchere.common.util.NetworkUtil;

public class SellerClient extends Client {
    ClientSocketWrapper toManager;

    public void connectToManager() {
        SSLSocketFactory factory = new SSLSocketFactory();
        toManager = new ClientSocketWrapper(factory.createSocket(NetworkUtil.MANAGER_SOCKET_ADDRESS));
    }

    public void sendBid(SigPack_Bid bid) {
        fetchWithData(toManager, Headers.NEW_BID, Headers.OK_NEW_BID, bid);
    }

    public SigPack_PriceWin sendEncryptedOffersProduct(SigPack_EncOffersProduct prices) {
        return fetchWithData(toManager, Headers.RESOLVE_BID, Headers.RESOLVE_BID_OK, prices);
    }

    public void stopError() {
        abort(toManager);
    }

    public void stopManager()
    {
        stop(toManager);
    }
}