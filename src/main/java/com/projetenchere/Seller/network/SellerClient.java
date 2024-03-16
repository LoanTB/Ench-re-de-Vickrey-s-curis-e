package com.projetenchere.Seller.network;

import com.projetenchere.common.Models.Bid;
import com.projetenchere.common.Models.Encrypted.SigPack_EncOffersProduct;
import com.projetenchere.common.Models.Encrypted.SigPack_PriceWin;
import com.projetenchere.common.Utils.NetworkUtil;
import com.projetenchere.common.network.Client;
import com.projetenchere.common.network.ClientSocketWrapper;
import com.projetenchere.common.network.Headers;
import com.projetenchere.common.network.socket.SSLSocketFactory;

import java.security.Signature;

public class SellerClient extends Client {
    ClientSocketWrapper toManager;
    Signature sellerSignature;

    public void connectToManager() {
        SSLSocketFactory factory = new SSLSocketFactory();
        toManager = new ClientSocketWrapper(factory.createSocket(NetworkUtil.MANAGER_SOCKET_ADDRESS));
    }

    public void sendBid(Bid bid) {
        fetchWithData(toManager, Headers.NEW_BID, Headers.OK_NEW_BID, bid);
    }

    public SigPack_PriceWin sendEncryptedOffersSet(SigPack_EncOffersProduct prices) {
        return fetchWithData(toManager, Headers.RESOLVE_BID, Headers.RESOLVE_BID_OK, prices);
    }
}