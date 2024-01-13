package com.projetenchere.common.Utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

public class NetworkUtil {

    public static final InetSocketAddress MANAGER_SOCKET_ADDRESS = new InetSocketAddress(ipManager(), 24683);
    public static final int SELLER_PORT = 24682;

    public static String ipManager() {
        try {
            String destinationPath = System.getProperty("user.home") + "/.config/securewin/managerIp.txt";
            FileInputStream file = new FileInputStream(destinationPath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(file));
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getMyIP() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "127.0.0.1";
        }
    }

}
