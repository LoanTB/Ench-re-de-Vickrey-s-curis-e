package com.projetenchere.common.network.socket;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public interface ISocketFactory {

    Socket createSocket(InetSocketAddress address);

    ServerSocket createServerSocket(int port);
}
