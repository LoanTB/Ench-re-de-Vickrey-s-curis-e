package com.projetenchere.common.Utils;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

public class NetworkUtil {

    public static final InetSocketAddress MANAGER_SOCKET_ADDRESS = new InetSocketAddress("127.0.0.1", 24683);
    public static final int SELLER_PORT = 24682;
    public static String getMyIP(){
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch(UnknownHostException e) {
            return "127.0.0.1";
        }
    }

}
